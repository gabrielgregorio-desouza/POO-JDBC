package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import entities.Aluno;
import jdbc.AlunoJDBC;
import jdbc.DB;

public class Programa {

	public static void main(String[] args) throws IOException, SQLException {

		Connection con = DB.getConexao();
		System.out.println("Conexão realizada com sucesso !");
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		
		Scanner console = new Scanner(System.in);
		AlunoJDBC acao= null;
		
		int opcao = 0;
		
		try {

			do {
				System.out.print("######## Menu ########" + 
								"\n1- Cadastrar" + 
								"\n2- Listar"    + 
								"\n3- Alterar"   +
								"\n4- Excluir"   + 
								"\n5- Sair"      +
								"\n\tOpção: ");
				opcao = Integer.parseInt(console.nextLine());

				if (opcao == 1) {

					Aluno a = new Aluno();
				//	AlunoJDBC acao = new AlunoJDBC();

					System.out.print("\n*** Cadastrar Aluno ***\n\r");
					System.out.print("Nome: ");
					a.setNome(console.nextLine());
					System.out.print("Sexo: ");
					a.setSexo(console.nextLine());
		
					System.out.print("Data de nascimento (dd/MM/yyyy): ");
					a.setDt_nasc( Date.valueOf( LocalDate.parse( console.nextLine(), formato) ) ) ;
					
					
					acao.salvar(a, con);
					console.nextLine();
				}
				if (opcao == 2) {
					System.out.print("\n*** Listar Alunos ***\n\r");
					List<Aluno> lista = AlunoJDBC.listar(con);
					
					for (Aluno a : lista) {
						System.out.println("Aluno:" + a.getId()+
								"  "+ a.getNome()+
								"  "+ a.getSexo()+
								"  " +new SimpleDateFormat("dd-MM-yyyy").format(a.getDt_nasc()));
					
					}
				lista=acao.listar(con);
				
				
					console.nextLine();
				}
				
				if(opcao == 3) {
					Aluno a = new Aluno();
					System.out.println("\n*** ALTERAR ALUNO ***\n\r");
					System.out.println("Informe o id do aluno que deseja ALTERAR: ");
					a.setId(console.nextInt());
					console.nextLine();
					
					System.out.println("Informe o nome: ");
					a.setNome(console.nextLine());
					
					System.out.println("Informe o sexo: ");
					a.setSexo(console.nextLine());
					
					System.out.print("Informe a Data de nascimento (dd/MM/yyyy): ");
					a.setDt_nasc( Date.valueOf( LocalDate.parse( console.nextLine(), formato) ) ) ;
					
					
					
					AlunoJDBC.alterar(a, con);
				}
				if(opcao == 4) {
					System.out.println("\n*** APAGAR ALUNO ***\n\r");
					System.out.println("Digite o id do aluno que deseja apagar: ");
					int  id = console.nextInt();
					console.nextLine();
					AlunoJDBC.apagar(id, con);
				}
				
			} while (opcao != 5);

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
		
		DB.fechaConexao();
		System.out.println("Conexão fechada com sucesso !");
	}
}
