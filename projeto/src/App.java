import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Carrega o driver (opcional em JDBC novo, mas pode deixar)
        Class.forName("org.postgresql.Driver");

        System.out.println("Iniciando conexão...");

        // 🔑 Troque se necessário pela URL do seu Supabase (Connect -> JDBC)
        String url = "jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.eezzitrfnjebnspdayyl&password=Programaçãodesistemasii";

        try (Connection con = DriverManager.getConnection(url)) {
            System.out.println("Conexão ok!");

            // ==== EXEMPLO DE USO ====
            listar(con);  // mostra registros atuais

            inserir(con, "antonio", "658412365-59"); // insere novo
            atualizar(con, 1, "João Atualizado");   // altera nome do id 1
            deletar(con, 2);                        // apaga id 2

            listar(con);  // mostra registros após operações
        }
    }

    // ===== LISTAR =====
    public static void listar(Connection con) throws SQLException {
        System.out.println("\n--- LISTANDO PROPRIETARIOS ---");
        Statement smtm = con.createStatement();
        ResultSet resultado = smtm.executeQuery("SELECT * FROM PROPRIETARIOS");

        while (resultado.next()) {
            System.out.println("ID: " + resultado.getInt("ID") +
                               ", Nome: " + resultado.getString("NOME") +
                               ", CPF: " + resultado.getString("CPF"));
        }
    }

    // ===== INSERIR =====
    public static void inserir(Connection con, String nome, String cpf) throws SQLException {
        String sql_insert = "INSERT INTO PROPRIETARIOS (NOME, CPF) VALUES (?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql_insert);
        pstmt.setString(1, nome);
        pstmt.setString(2, cpf);

        int qte = pstmt.executeUpdate();
        if (qte >= 1) {
            System.out.println("Inserido com sucesso: " + nome);
        }
    }

    // ===== ATUALIZAR =====
    public static void atualizar(Connection con, int id, String novoNome) throws SQLException {
        String sql_update = "UPDATE PROPRIETARIOS SET NOME = ? WHERE ID = ?";
        PreparedStatement pstmt = con.prepareStatement(sql_update);
        pstmt.setString(1, novoNome);
        pstmt.setInt(2, id);

        int qte = pstmt.executeUpdate();
        if (qte >= 1) {
            System.out.println("Atualizado com sucesso! ID = " + id);
        } else {
            System.out.println("Nenhum registro encontrado para atualizar. ID = " + id);
        }
    }

    // ===== DELETAR =====
    public static void deletar(Connection con, int id) throws SQLException {
        String sql_delete = "DELETE FROM PROPRIETARIOS WHERE ID = ?";
        PreparedStatement pstmt = con.prepareStatement(sql_delete);
        pstmt.setInt(1, id);

        int qte = pstmt.executeUpdate();
        if (qte >= 1) {
            System.out.println("Deletado com sucesso! ID = " + id);
        } else {
            System.out.println("Nenhum registro encontrado para deletar. ID = " + id);
        }
    }
}
