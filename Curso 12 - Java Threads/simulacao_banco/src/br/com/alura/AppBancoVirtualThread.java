package br.com.alura;

import java.math.BigDecimal;

public class AppBancoVirtualThread {

    public static void main(String[] args) {
        var cliente = new Cliente("João");
        var conta = new Conta(cliente, new BigDecimal("150"));

        var operacao = new OperacaoSaque(conta, new BigDecimal("150"));

        Thread saqueJoao = Thread.startVirtualThread(operacao);
        Thread saqueMaria = Thread.startVirtualThread(operacao);

        System.out.println(Thread.currentThread().getName());

        try {
            saqueJoao.join();
            saqueMaria.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("saldo final" + conta.getSaldo());

    }
}
