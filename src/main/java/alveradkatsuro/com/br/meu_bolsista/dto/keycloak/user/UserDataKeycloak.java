package alveradkatsuro.com.br.meu_bolsista.dto.keycloak.user;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataKeycloak {

    private String id;

    private long createdTimestamp;

    private String username;

    private boolean enabled;

    private boolean totp;

    private boolean emailVerified;

    private String firstName;

    private String lastName;

    private String email;

    private Map<String, List<Object>> attributes;

    private List<String> disableableCredentialTypes;

    private List<String> requiredActions;

    private long notBefore;

    private Access access;

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
