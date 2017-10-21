/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testBack2017;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author muril
 */
public class TestBack2017 {

   /**
     * @param args the command line arguments
     */
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "pwd";

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

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private static void batchSelectRecordsIntoTable() throws SQLException {

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        // id auto increment    
        String selectSQL = "SELECT id_customer, nm_customer, is_active, vl_total, cpf_cnpj\n"
                + "FROM desafio.tb_customer_account WHERE vl_total > 560  AND id_customer > 1500 AND id_customer < 2700 ORDER BY vl_total DESC";

        try {
            dbConnection = getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            dbConnection.setAutoCommit(false);
            ResultSet rs = preparedStatement.executeQuery();
            Object[] record;
            int countdown = 1;
            int total = 0;
            String test = "";
            ArrayList<Object[]> dataCliente = new ArrayList<>();
            while (rs.next()) {
                record = new Object[rs.getMetaData().getColumnCount()];
                countdown++;
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    record[i] = rs.getString(i + 1);
                    if (record[3] != null) {
                        test = (String) record[3];
                    }
                }
                total = total + Integer.parseInt(test);
                dataCliente.add(record);
            }
            System.out.println("Número de Clientes no banco com o saldo maior que 560 reais e com id entre 1500 e 2700 é de " + (countdown - 1));
            System.out.println("Média final de todos os valores dos clientes é de " + total / (countdown - 1) + " reais");
            System.out.println("====================================================================");
            countdown = 1;
            for (Object[] dataCliente1 : dataCliente) {
                System.out.println("Cliente " + countdown);
                System.out.println("ID: " + dataCliente1[0]);
                System.out.println("Nome: " + dataCliente1[1]);
                System.out.println("Saldo: R$" + dataCliente1[3]);
                System.out.println("CPF/CNPJ: " + dataCliente1[4] + "\n");
                countdown++;
            }
            dbConnection.commit();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static void batchInsertRecordsIntoTable(String nm_customer, String cpf_cnpj, long vl_total, boolean active, int id_customer) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        // id auto increment    
        String insertSQL = "INSERT INTO desafio.tb_customer_account("
                + "id_customer, nm_customer, is_active, vl_total, cpf_cnpj)"
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            dbConnection = getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertSQL);

            dbConnection.setAutoCommit(false);

            preparedStatement.setInt(1, id_customer);
            preparedStatement.setString(2, nm_customer);
            preparedStatement.setBoolean(3, active);
            preparedStatement.setLong(4, vl_total);
            preparedStatement.setString(5, cpf_cnpj);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();

            dbConnection.commit();
            JOptionPane.showMessageDialog(null, "Cliente foi cadastrado com sucesso!");
            System.out.println("Cliente foi cadastrado com sucesso!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

}
