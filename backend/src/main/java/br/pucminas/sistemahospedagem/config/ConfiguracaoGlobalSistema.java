package br.pucminas.sistemahospedagem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class ConfiguracaoGlobalSistema {
    private int checkoutHora;
}