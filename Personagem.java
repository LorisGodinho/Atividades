package LorisAula18082020;

public class Personagem {
	private Long id;
	private String nome;
	private Double tipo;

	public Personagem(Long id, String nome, Double tipo) {
		this(nome, tipo);
		this.id = id;		
	}

	public Personagem(String nome, Double metragem) {
		this.nome = nome;
		this.tipo = metragem;
	}
	
	public String getNome() {
		return nome;
	}
	public Double getTipo() {
		return tipo;
	}
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Personagem [id=" + id + ", nome=" + nome + ", tipo=" + tipo + "]";
	}
}
