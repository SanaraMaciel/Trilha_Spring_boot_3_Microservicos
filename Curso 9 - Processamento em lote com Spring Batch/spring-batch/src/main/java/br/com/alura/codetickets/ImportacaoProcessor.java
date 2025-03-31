package br.com.alura.codetickets;

import org.springframework.batch.item.ItemProcessor;

public class ImportacaoProcessor implements ItemProcessor<Importacao, Importacao> {

    /**
     * processamento de cobrança uma taxa de administração baseada no tipo de ingresso que a pessoa está comprando.
     * @param item
     * @return
     * @throws Exception
     */
    @Override
    public Importacao process(Importacao item) throws Exception {

        if (item.getTipoIngresso().equalsIgnoreCase("vip")) {
            item.setTaxaAdm(130.0);
        } else if (item.getTipoIngresso().equalsIgnoreCase("camarote")) {
            item.setTaxaAdm(80.0);
        } else {
            item.setTaxaAdm(50.0);
        }

        return item;
    }

}
