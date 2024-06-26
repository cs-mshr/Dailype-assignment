package com.dailype.assignment.service.Impl;

import com.dailype.assignment.model.User;
import com.dailype.assignment.pojo.enums.Status;
import com.dailype.assignment.pojo.enums.UserDetails;
import com.dailype.assignment.pojo.request.CreateUserRequest;
import com.dailype.assignment.pojo.request.UpdateUserRequest;
import com.dailype.assignment.pojo.request.UpdatedDataForm;
import com.dailype.assignment.pojo.request.ValidateUserDetailsRequest;
import com.dailype.assignment.pojo.response.CreateUserResponse;
import com.dailype.assignment.pojo.response.ValidateUserDetailsResponse;
import com.dailype.assignment.service.ManagerService;
import com.dailype.assignment.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserValidatorServiceImpl implements UserValidatorService {

    @Autowired
    private ManagerService managerService;

    // Regular expression for a valid PAN number
    private static final String PAN_REGEX = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

    // Regular expression for a valid 10-digit mobile number
    private static final String MOBILE_NUMBER_REGEX = "(0|\\+91)?[7-9][0-9]{9}";

    @Override
    public ValidateUserDetailsResponse validateUser(ValidateUserDetailsRequest validateUserDetailsRequest) {

        ValidateUserDetailsResponse validateUserDetailsResponse = new ValidateUserDetailsResponse();
        User incorrect_user_detail = new User();

        if (!validateFullName(validateUserDetailsRequest.getFull_name())) {
            incorrect_user_detail.setFullName(validateUserDetailsRequest.getFull_name());

            validateUserDetailsResponse.setStatus(Status.FAILED);
            validateUserDetailsResponse.setUserDetails(UserDetails.FULL_NAME_CANNOT_BE_EMPTY);
            validateUserDetailsResponse.setUser(incorrect_user_detail);

            return validateUserDetailsResponse;
        }

        if (!validateMobileNumber(validateUserDetailsRequest.getMob_num())) {
            incorrect_user_detail.setMobNum(validateUserDetailsRequest.getMob_num());

            validateUserDetailsResponse.setStatus(Status.FAILED);
            validateUserDetailsResponse.setUserDetails(UserDetails.INVALID_MOBILE_NUMBER);
            validateUserDetailsResponse.setUser(incorrect_user_detail);

            return validateUserDetailsResponse;
        }

        if (!validatePanNumber(validateUserDetailsRequest.getPan_num())) {
            incorrect_user_detail.setPanNum(validateUserDetailsRequest.getPan_num());

            validateUserDetailsResponse.setStatus(Status.FAILED);
            validateUserDetailsResponse.setUserDetails(UserDetails.INVALID_PAN_NUMBER);
            validateUserDetailsResponse.setUser(incorrect_user_detail);

            return validateUserDetailsResponse;
        }

        if (!validateManagerId(validateUserDetailsRequest.getManager_id())) {
            incorrect_user_detail.setManagerId(validateUserDetailsRequest.getManager_id());

            validateUserDetailsResponse.setStatus(Status.FAILED);
            validateUserDetailsResponse.setUserDetails(UserDetails.INVALID_MANAGER_ID);
            validateUserDetailsResponse.setUser(incorrect_user_detail);

            return validateUserDetailsResponse;
        }

        validateUserDetailsResponse.setStatus(Status.SUCCESS);
        validateUserDetailsResponse.setUserDetails(UserDetails.INSERTED_ALL_FIELDS);
        return validateUserDetailsResponse;
    }

    @Override
    public ValidateUserDetailsResponse userDetailsForUpdate(UpdatedDataForm updatedDataForm) {
        ValidateUserDetailsResponse validateUserDetailsResponse = new ValidateUserDetailsResponse();
        User incorrect_user_detail = new User();

        if(updatedDataForm.getFull_name() != null){
            if(!validateFullName(updatedDataForm.getFull_name())) {
                incorrect_user_detail.setFullName(updatedDataForm.getFull_name());

                validateUserDetailsResponse.setStatus(Status.FAILED);
                validateUserDetailsResponse.setUserDetails(UserDetails.FULL_NAME_CANNOT_BE_EMPTY);
                validateUserDetailsResponse.setUser(incorrect_user_detail);
            }
        }

        if(updatedDataForm.getPan_num() != null){
            if(!validatePanNumber(updatedDataForm.getPan_num())) {
                incorrect_user_detail.setPanNum(updatedDataForm.getPan_num());

                validateUserDetailsResponse.setStatus(Status.FAILED);
                validateUserDetailsResponse.setUserDetails(UserDetails.INVALID_PAN_NUMBER);
                validateUserDetailsResponse.setUser(incorrect_user_detail);
            }
        }

        if(updatedDataForm.getManager_id() != null){
            if(!validateManagerId(updatedDataForm.getManager_id())) {
                incorrect_user_detail.setManagerId(updatedDataForm.getManager_id());

                validateUserDetailsResponse.setStatus(Status.FAILED);
                validateUserDetailsResponse.setUserDetails(UserDetails.INVALID_MANAGER_ID);
                validateUserDetailsResponse.setUser(incorrect_user_detail);
            }
        }

        if(updatedDataForm.getMob_num() != null){
            if(!validateMobileNumber(updatedDataForm.getMob_num())) {
                incorrect_user_detail.setMobNum(updatedDataForm.getMob_num());
                validateUserDetailsResponse.setStatus(Status.FAILED);
                validateUserDetailsResponse.setUserDetails(UserDetails.INVALID_MOBILE_NUMBER);
                validateUserDetailsResponse.setUser(incorrect_user_detail);
            }
        }

        if(validateUserDetailsResponse.getUserDetails() == null)
            validateUserDetailsResponse.setUserDetails(UserDetails.ALL_FIELDS_ARE_VALID);
        return validateUserDetailsResponse;
    }

    @Override
    public boolean validateFullName(String fullName) {
        return fullName != null && !fullName.trim().isEmpty();
    }

    @Override
    public boolean validateMobileNumber(String mobNum) {
        return mobNum != null && mobNum.matches(MOBILE_NUMBER_REGEX);
    }

    @Override
    public boolean validatePanNumber(String panNum) {
        return panNum != null && panNum.toUpperCase().matches(PAN_REGEX);
    }

    @Override
    public boolean validateManagerId(UUID managerId) {
        return managerService.isManagerActive(managerId);
    }


    @Override
    public String alterMobNo(String mobNum) {
        String digitsOnly = mobNum.replaceAll("\\D", "");

        // Adjust the number based on prefixes
        if (digitsOnly.startsWith("0")) {
            // Remove leading zero
            digitsOnly = digitsOnly.substring(1);
        } else if (digitsOnly.startsWith("91")) {
            // Remove country code "91"
            digitsOnly = digitsOnly.substring(2);
        }

        // Ensure the number has exactly 10 digits
        if (digitsOnly.length() == 10) {
            return digitsOnly;
        } else {
            // Handle invalid mobile number
            throw new IllegalArgumentException("Invalid mobile number");
        }
    }

}
