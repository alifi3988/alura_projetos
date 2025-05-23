package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "abrigos")
public class Abrigo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  private String telefone;

  private String email;

  @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference("abrigo_pets")
  private List<Pet> pets;

  public Abrigo(CadastroAbrigoDto abrigoDto) {
    this.nome = abrigoDto.nome();
    this.telefone = abrigoDto.telefone();
    this.email = abrigoDto.email();
  }

  public Abrigo() {}

  public void adicionarPetEmAbrigo(Pet pet) {
    this.pets.add(pet);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Abrigo abrigo = (Abrigo) o;
    return Objects.equals(id, abrigo.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Pet> getPets() {
    return pets;
  }

  public void setPets(List<Pet> pets) {
    this.pets = pets;
  }
}
