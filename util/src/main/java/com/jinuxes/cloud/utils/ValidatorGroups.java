package com.jinuxes.cloud.utils;

import javax.validation.groups.Default;

public class ValidatorGroups {
    public interface AddUser extends Default{};
    public interface UpdateUser extends Default{};
    public interface UpdateUserPassword extends Default{};
}
