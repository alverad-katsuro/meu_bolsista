package alveradkatsuro.com.br.meu_bolsista.dto.keycloak.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealmRoleKeycloak {

    private String id;

    private String name;

    private String description;

    private String composite;

    private String clientRole;

    private String containerId;

}
