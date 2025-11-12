package com.example.frontend.api;

import com.example.frontend.model.DonorProfileFormData;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * API helper for creating or updating donor profiles.
 */
public final class DonorProfilesApi {

    private static final String BASE_URL = "http://localhost:3002/api/Donor_Profiles";

    private DonorProfilesApi() {}

    /**
     * Sends a donor profile payload to the backend.
     *
     * @param data user-supplied donor profile fields
     * @return {@code true} if the request succeeded
     */
    public static boolean submitProfile(DonorProfileFormData data) throws ApiException {
        if (data.getDonorId() == null) {
            throw new ApiException("Donor ID is required", 400);
        }
        if (data.getBloodType() == null || data.getRhesus() == null) {
            throw new ApiException("Blood type and rhesus factor are required", 400);
        }

        try {
            ObjectNode payload = ApiClient.MAPPER.createObjectNode();
            payload.put("bloodType", data.getBloodType());
            payload.put("rhesus", data.getRhesus());
            payload.put("availabilityStatus", data.getAvailabilityStatus());
            payload.put("homeAddress", data.getHomeAddress());
            payload.put("location", data.getHomeAddress());
            if (data.getDateOfBirth() != null) {
                payload.put("dateOfBirth", data.getDateOfBirth().toString());
            }
            putDateTime(payload, "availableBy", data.getAvailableBy());
            putDateTime(payload, "doNotDisturbUntil", data.getDoNotDisturbUntil());
            putDateTime(payload, "lastDonationDate", data.getLastDonationDate());
            if (data.getPreferredRadiusKm() != null) {
                payload.put("preferredRadiusKm", data.getPreferredRadiusKm());
            }
            payload.put("donationsCount", 0);

            ObjectNode userNode = payload.putObject("user");
            userNode.put("userId", data.getDonorId());

            java.util.Map<String, String> headers = new java.util.HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = ApiClient.request("POST", BASE_URL, payload.toString(), headers);
            return response != null;
        } catch (IOException e) {
            throw new ApiException("Unable to reach donor profile endpoint: " + e.getMessage(), 500);
        }
    }

    private static void putDateTime(ObjectNode node, String field, LocalDateTime value) {
        if (value != null) {
            node.put(field, value.toString());
        }
    }
}
