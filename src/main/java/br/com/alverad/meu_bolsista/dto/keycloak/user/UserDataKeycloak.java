package br.com.alverad.meu_bolsista.dto.keycloak.user;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataKeycloak {

    @NotBlank(message = "ID é obrigatório")
    private String id;

    private long createdTimestamp;

    @NotBlank(message = "Username é obrigatório")
    private String username;

    private boolean enabled;

    private boolean totp;

    private boolean emailVerified;

    @NotBlank(message = "Primeiro Nome é obrigatório")
    private String firstName;

    @NotBlank(message = "Último Nome é obrigatório")
    private String lastName;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    private Map<String, List<Object>> attributes;

    private List<String> disableableCredentialTypes;

    private List<String> requiredActions;

    private long notBefore;

    private Access access;

    @JsonIgnore
    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    @JsonIgnore
    public String getPicture() {
        if (attributes != null && attributes.containsKey("picture")) {
            List<Object> pictureObj = attributes.get("picture");
            if (!pictureObj.isEmpty()) {
                return (String) pictureObj.get(0);
            }
        }
        return null;
    }

    @JsonIgnore
    public String getLattes() {
        if (attributes != null && attributes.containsKey("lattes")) {
            List<Object> pictureObj = attributes.get("lattes");
            if (!pictureObj.isEmpty()) {
                return (String) pictureObj.get(0);
            }
        }
        return null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Access {

        private boolean manageGroupMembership;

        private boolean view;

        private boolean mapRoles;

        private boolean impersonate;

        private boolean manage;

    }
}
