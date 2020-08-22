package LorisAula18082020;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonagemRepository {

	private ConnectionManager connectionManager;

	public PersonagemRepository(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
		this.createTable();		
	}
		

	private void createTable() {
		PreparedStatement psCreateTable = null;
		try {
			psCreateTable =  connectionManager.prepareStatement("create table if not exists Personagem ("
					+ "id long not null primary key,"
					+ "nome varchar(255) not null,"
					+ "tipo number(9,2) not null"
					+ ")");
			psCreateTable.executeLargeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psCreateTable != null) {
				try {
					psCreateTable.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}



	public Personagem findById(Long id) throws SQLException {
		Personagem found = null;
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, nome, tipo from Personagem where id = ?");
		psSelect.setLong(1, id);
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				found = new Personagem(
						rsSelect.getLong("id"), 
						rsSelect.getString("nome"), 
						rsSelect.getDouble("tipo")); 
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return found;
	}

	public Personagem save(Personagem Personagem) {
		boolean exists = Personagem.getId() != null;
		PreparedStatement psSave = null;
		try {
			if (exists) {
				psSave = connectionManager.prepareStatement("update Personagem set nome = ?, tipo = ? where id = ?");
				psSave.setString(1, Personagem.getNome());
				psSave.setDouble(2, Personagem.getTipo());
				psSave.setLong(3, Personagem.getId());
			} else {
				Long id = selectNewId();
				System.out.println("Novo id: " + id);
				psSave = connectionManager.prepareStatement("insert into Personagem (id, nome, tipo) values (?,?,?)");
				psSave.setLong(1, id);
				psSave.setString(2, Personagem.getNome());
				psSave.setDouble(3, Personagem.getTipo());
				Personagem = new Personagem(id,  Personagem.getNome(), Personagem.getTipo());
			}
			psSave.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				psSave.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Personagem;
	}

	private Long selectNewId() throws SQLException {
		PreparedStatement psSelect = connectionManager.prepareStatement("select coalesce(max(id),0)+1 as newId from Personagem");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				return rsSelect.getLong("newId");
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return null;
	}


	public void deleteById(Long id) throws SQLException {
		PreparedStatement psDelete = connectionManager.prepareStatement("delete from Personagem where id = ?");
		psDelete.setLong(1, id);
		try {
			psDelete.executeUpdate();
		} finally {
			psDelete.close();
		}		
	}


	public List<Personagem> findAll() throws SQLException {
		List<Personagem> all = new ArrayList<>();
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, nome, tipo from Personagem");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			while (rsSelect.next()) {
				Personagem Personagem = new Personagem(
						rsSelect.getLong("id"), 
						rsSelect.getString("nome"), 
						rsSelect.getDouble("tipo"));
				all.add(Personagem);
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return all;
	}



}	
