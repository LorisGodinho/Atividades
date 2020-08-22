package LorisAula18082020;

import java.sql.SQLException;
import java.util.List;

public class AppRepository {

	public static void main(String[] args) throws SQLException {
		ConnectionManager connManager = new ConnectionManager();
		try {
			PersonagemRepository repo = new PersonagemRepository(connManager);
			
			Personagem Jackfrost = new Personagem("Jack Haminston Frost", 17.42);
			Jackfrost = repo.save(Jackfrost);
			connManager.commit();
			
			Long id = Jackfrost.getId();
			System.out.println("Procurando pelo id: " + id);

			
			Personagem encontradoPeloId = repo.findById(id);
			System.out.println(encontradoPeloId.toString());
			
			repo.deleteById(id);
			System.out.println("Recuperado após exclusão: " + repo.findById(id));
			
			List<Personagem> imóveisCadastrados = repo.findAll();
			System.out.println("Todos os Personagens >>>>");
			for (Personagem personagem : imóveisCadastrados) {
				System.out.println(personagem.toString());
			}
			System.out.println("<<<< Fim.");

			
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			connManager.close();
		}
	}

}
