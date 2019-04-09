package fr.hoc.dap.server.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.hoc.dap.server.service.AdminService;

/**
 * Controller who handles the administration panel.
 * @author Mohammed & Thomas
 *
 */
@Controller
public class AdminController {
    /**
     * Injection of dependency with Autowired annotation.
     */
    @Autowired
    private AdminService accService;

    /**
     * @param model Map Object loaded with results
     * @return Admin View
     * @throws GeneralSecurityException Security Exception
     * @throws IOException IOException
     */
    @RequestMapping("/admin")
    public String admin(final ModelMap model) throws GeneralSecurityException, IOException {
        DataStore<StoredCredential> usersInfo = accService.getCredentialMap();

        Map<String, StoredCredential> userMap = new HashMap<>();
        Set<String> keys = usersInfo.keySet();

        for (String key : keys) {
            StoredCredential value = usersInfo.get(key);
            userMap.put(key, value);
        }
        model.addAttribute("users", userMap);

        return "admin";

    }

    /**
     * @param userKey the username you want to delete.
     * @return redirect to the admin homepage
     * @throws GeneralSecurityException Security Exception
     * @throws IOException IOException
     */
    @RequestMapping("/delete/user")
    public String deleteuser(final String userKey) throws GeneralSecurityException, IOException {
        DataStore<StoredCredential> deleteUser = accService.getCredentialMap().delete(userKey);
        return "redirect:/admin";

    }
}
