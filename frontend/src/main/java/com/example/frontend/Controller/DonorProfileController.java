package com.example.frontend.Controller;

import com.example.frontend.api.ApiException;
import com.example.frontend.api.DonorProfilesApi;
import com.example.frontend.model.DonorProfileFormData;
import com.example.frontend.model.UserModel;
import com.example.frontend.utils.SceneNavigator;
import com.example.frontend.utils.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controller for the donor profile form displayed after the Donor role is selected.
 */
public class DonorProfileController {

    @FXML private TextField donorIdField;
    @FXML private ComboBox<String> bloodTypeCombo;
    @FXML private ComboBox<String> rhesusCombo;
    @FXML private ComboBox<String> availabilityCombo;
    @FXML private DatePicker dateOfBirthPicker;
    @FXML private DatePicker dndDatePicker;
    @FXML private TextField dndTimeField;
    @FXML private TextField homeAddressField;
    @FXML private DatePicker availableByDatePicker;
    @FXML private TextField availableByTimeField;
    @FXML private DatePicker lastDonationDatePicker;
    @FXML private Slider radiusSlider;
    @FXML private Label radiusValueLabel;
    @FXML private Label statusLabel;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    /** Initializes dropdowns and slider labels once the view loads. */
    @FXML
    public void initialize() {
        bloodTypeCombo.setItems(FXCollections.observableArrayList("A", "B", "AB", "O"));
        rhesusCombo.setItems(FXCollections.observableArrayList("+", "-"));
        availabilityCombo.setItems(FXCollections.observableArrayList(
                "Available", "Unavailable", "Do Not Disturb"));

        radiusSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                radiusValueLabel.setText(String.format("%d km", newVal.intValue())));
        radiusValueLabel.setText(String.format("%d km", (int) radiusSlider.getValue()));

        prefillFromSession();
    }

    private void prefillFromSession() {
        UserModel user = Session.getCurrentUser();
        if (user == null) {
            return;
        }
        if (user.getUserId() != null) {
            donorIdField.setText(String.valueOf(user.getUserId()));
        }
        if (user.getBloodType() != null) {
            bloodTypeCombo.setValue(user.getBloodType());
        }
        if (user.getRhesus() != null) {
            rhesusCombo.setValue(user.getRhesus());
        }
        if (user.getHomeAddress() != null) {
            homeAddressField.setText(user.getHomeAddress());
        }
    }

    @FXML
    private void handleSubmit() {
        statusLabel.setText("");
        DonorProfileFormData data = collectFormData();
        if (data == null) {
            return;
        }

        try {
            boolean saved = DonorProfilesApi.submitProfile(data);
            if (saved) {
                setStatus("Donor profile submitted successfully.", true);
            } else {
                setStatus("Profile submission failed. Please try again.", false);
            }
        } catch (ApiException ex) {
            setStatus(ex.getMessage(), false);
        }
    }

    private DonorProfileFormData collectFormData() {
        String donorIdText = donorIdField.getText().trim();
        if (donorIdText.isEmpty()) {
            setStatus("Donor ID is required.", false);
            return null;
        }

        Long donorId;
        try {
            donorId = Long.parseLong(donorIdText);
        } catch (NumberFormatException ex) {
            setStatus("Donor ID must be numeric.", false);
            return null;
        }

        String bloodType = bloodTypeCombo.getValue();
        String rhesus = rhesusCombo.getValue();
        String availability = availabilityCombo.getValue();
        String homeAddress = homeAddressField.getText().trim();

        if (bloodType == null || rhesus == null || availability == null || homeAddress.isEmpty()) {
            setStatus("Please complete all required profile fields.", false);
            return null;
        }

        DonorProfileFormData data = new DonorProfileFormData();
        data.setDonorId(donorId);
        data.setBloodType(bloodType);
        data.setRhesus(rhesus);
        data.setAvailabilityStatus(availability);
        data.setHomeAddress(homeAddress);
        data.setPreferredRadiusKm((int) Math.round(radiusSlider.getValue()));
        data.setDateOfBirth(dateOfBirthPicker.getValue());
        try {
            data.setDoNotDisturbUntil(combineDateAndTime(dndDatePicker, dndTimeField));
            data.setAvailableBy(combineDateAndTime(availableByDatePicker, availableByTimeField));
            data.setLastDonationDate(combineDateAndTime(lastDonationDatePicker, null));
        } catch (DateTimeParseException ex) {
            setStatus("Invalid time format. Please use HH:MM (24h).", false);
            return null;
        }

        return data;
    }

    private LocalDateTime combineDateAndTime(DatePicker picker, TextField timeField) {
        LocalDate date = picker != null ? picker.getValue() : null;
        if (date == null) {
            return null;
        }
        LocalTime time = LocalTime.MIDNIGHT;
        if (timeField != null && !timeField.getText().trim().isEmpty()) {
            String raw = timeField.getText().trim();
            time = LocalTime.parse(raw, TIME_FORMAT);
        }
        return LocalDateTime.of(date, time);
    }

    private void setStatus(String message, boolean success) {
        statusLabel.setStyle(success ? "-fx-text-fill: #2b7a0b;" : "-fx-text-fill: #b00020;");
        statusLabel.setText(message);
    }

    @FXML
    private void handleBack() {
        try {
            SceneNavigator.switchScene("Dashboard-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
