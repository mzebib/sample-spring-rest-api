package com.github.mzebib.provisioningapi.data;

import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.OrgInfo;

/**
 * @author mzebib
 */
public final class TestData {

    private TestData() {
    }

    public static CreateUser createUserInfo() {
        CreateUser createUser = new CreateUser();
        createUser.setFirstName("Test");
        createUser.setLastName("User");
        createUser.setEmail(System.currentTimeMillis() + "@example.com");
        createUser.setUsername("" + System.currentTimeMillis());
        createUser.setPassword("testpwd");

        return createUser;
    }

    public static OrgInfo createOrgInfo() {
        String name =  "" + System.currentTimeMillis();
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setName(name);
        orgInfo.setEmail(name + "@example.com");
        orgInfo.setAddress("123 Main St.");
        orgInfo.setCity("New York");
        orgInfo.setState("NY");
        orgInfo.setCountry("USA");
        orgInfo.setZipCode("10012");

        return orgInfo;
    }
}
