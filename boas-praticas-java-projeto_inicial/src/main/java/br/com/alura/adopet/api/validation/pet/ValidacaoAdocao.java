package br.com.alura.adopet.api.validation.pet;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;

public interface ValidacaoAdocao {

    void validar(SolicitacaoAdocaoDto dto );
}
