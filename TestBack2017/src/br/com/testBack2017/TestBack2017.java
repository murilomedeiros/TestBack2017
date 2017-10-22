/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testBack2017;

import static br.com.testBack2017.dataBase.CRUD.batchInsertRecordsIntoTable;
import static br.com.testBack2017.dataBase.CRUD.batchSelectRecordsIntoTable;
import static br.com.testBack2017.tools.Formatation.removerAcentos;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author muril
 */
public class TestBack2017 {
    public static void main(String[] argv) throws SQLException {

        try {
            String decision;
            decision = JOptionPane.showInputDialog("Deseja cadastrar algum cliente ? Sim ou não.");
            decision = removerAcentos(decision.toUpperCase());
            if (decision.equals("SIM") || decision.equals("YES") || decision.equals("S")) {
                do {
                    String id_customer = JOptionPane.showInputDialog("Id do cliente");
                    String nm_customer = JOptionPane.showInputDialog("Nome do cliente");
                    String cpf_cnpj = JOptionPane.showInputDialog("CPF ou CNPJ do cliente");
                    String vl_total = JOptionPane.showInputDialog("Saldo do cliente, em reais");
                    String is_active = JOptionPane.showInputDialog("Cliente está ativo ? Sim ou não.");
                    is_active = removerAcentos(is_active.toUpperCase());
                    if (is_active.equals("SIM") || is_active.equals("YES") || is_active.equals("S")) {
                        batchInsertRecordsIntoTable(nm_customer, cpf_cnpj, Long.parseLong(vl_total), true, Integer.parseInt(id_customer));
                    } else {
                        batchInsertRecordsIntoTable(nm_customer, cpf_cnpj, Long.parseLong(vl_total), false, Integer.parseInt(id_customer));
                    }
                    decision = JOptionPane.showInputDialog("Deseja cadastrar mais clientes ? Sim ou não.");
                    decision = removerAcentos(decision.toUpperCase());
                } while (decision.equals("SIM") || decision.equals("YES") || decision.equals("S"));
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        batchSelectRecordsIntoTable();

    }
}
