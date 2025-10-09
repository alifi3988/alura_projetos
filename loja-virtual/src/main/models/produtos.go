package produtos

import (
	"fmt"
	"loja-virtual/src/main/db"
)

type Produto struct {
	Id         int
	Nome       string
	Descricao  string
	Preco      float64
	Quantidade int
}

func BuscaTodosOsProdutos() []Produto {
	fmt.Println("✅ Iniciando o servidor de banco de dados...")
	db := db.ConectaComBancoDeDados()

	retorno, err := db.Query("SELECT * FROM produtos")

	if err != nil {
		panic(err.Error())
	}

	p := Produto{}

	produtos := []Produto{}

	for retorno.Next() {
		var id, quantidade int
		var nome, descricao string
		var preco float64

		err = retorno.Scan(&id, &nome, &descricao, &preco, &quantidade)
		if err != nil {
			panic(err.Error())
		}
		
		p.Id = id
		p.Nome = nome
		p.Descricao = descricao
		p.Preco = preco
		p.Quantidade = quantidade

		produtos = append(produtos, p)
	}

	defer db.Close()
	return produtos
}

func CriarNovoProduto(nome, descricao string, preco float64, quantidade int) error {
	db := db.ConectaComBancoDeDados()

	query, err := db.Prepare("INSERT INTO produtos(nome, descricao, preco, quantidade) VALUES (?, ?, ?, ?)")

	if err != nil {
		return err
	}

	query.Exec(nome, descricao, preco, quantidade)

	defer db.Close()

	fmt.Printf("✅ Produto %s crisdo com sucesso!", nome)

	return nil
}

func DeleteProduto(idProduto string) error {

	db := db.ConectaComBancoDeDados()

	query, err := db.Prepare("DELETE FROM produtos where idprodutos = ?")

	if err != nil {
		return err
	}

	query.Exec(idProduto)
	defer db.Close()

	fmt.Printf("✅ Produto %s deletado com sucesso!", idProduto)

	return nil
}

func BuscarProdutoPorId(idProduto string) (*Produto, error) {

	db := db.ConectaComBancoDeDados()

	var nome, descricao string
	var quantidade, id int
	var preco float64

	err := db.QueryRow("SELECT * FROM produtos where idprodutos=?", idProduto).Scan(&id, &nome, &descricao, &preco, &quantidade)
	if err != nil {
		return nil, err
	}

	p := &Produto{id, nome, descricao, preco,  quantidade}

	defer db.Close()

	fmt.Printf("✅ Produto %s consultado com sucesso!", idProduto)

	return p, nil
}

func AtualizarProduto(id, quantidade int, nome, descricao string, preco float64) error {
	
	fmt.Printf("✅ Atualizando o Produto %s...", nome)

	db := db.ConectaComBancoDeDados()

	query, err := db.Prepare("UPDATE produtos SET nome=?, descricao=?, preco=?, quantidade=? WHERE idprodutos=?")

	if err != nil {
		defer db.Close()
		return err
	}

	_, err = query.Exec(nome, descricao, preco, quantidade, id)

	if err != nil {
		defer db.Close()
		return err
	}

	defer db.Close()
	fmt.Printf("✅ Produto %s atualizado com sucesso!", nome)
	return nil
}
