package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {
	
	
	static PreparedStatement pst;
	static String sql;

	public static void salvar(Aluno a, Connection con) throws IOException {


		try {
			
			sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?,  ?, ?)";
			
			pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());
			
			Date dataSql = new Date(a.getDt_nasc().getTime());
			pst.setDate(3, dataSql);
			
			pst.executeUpdate();
			System.out.println("\nCadastro do aluno realizado com sucesso!");
			
		}
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
	}
	
	public static List<Aluno> listar(Connection con) throws SQLException {
		
	sql = "SELECT * FROM aluno";
	pst = con.prepareStatement(sql);
	ResultSet rs = pst.executeQuery();
	List <Aluno> lista= new ArrayList<>();
	while (rs.next()) {
		Aluno a = new Aluno(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4));
		lista.add(a);
	}
		return lista;
	}
	
	public static void apagar(int id, Connection con) throws SQLException {
		sql= "DELETE FROM aluno WHERE id= ?";
		pst = con.prepareStatement(sql);
		pst.setInt(1, id);
		
		
		if(pst.executeUpdate() >=1) {
			System.out.println("Aluno excluido com sucesso");
		}
		else {
			System.out.println("Nao foi encontrado aluno com id = "+ id + "!");
		}
		
	}
	
	public static void alterar(Aluno a, Connection con) throws SQLException {
		sql = "UPDATE aluno SET nome= ?, sexo= ?, dt_nasc= ? Where id=?";
		pst = con.prepareStatement(sql);
		pst.setString(1,a.getNome());
		pst.setString(2, a.getSexo());
		pst.setDate(3, a.getDt_nasc());
		pst.setInt(4, a.getId());
		
		if(pst.executeUpdate() >=1) {
			System.out.println("Aluno alterado com sucesso");
		}
		else {
			System.out.println("Nao foi encontrado aluno com ID "+ a.getId() + "!");
		}
		
	}
}

