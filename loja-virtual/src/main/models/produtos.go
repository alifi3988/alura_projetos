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

   produtosReturn, err := db.Query("SELECT * FROM produtos")

   if err != nil {
        panic(err.Error())
   }

   p := Produto{}

   produtos := []Produto{}

   for produtosReturn.Next() {
        var id, quantidade int
        var nome, descricao string
        var preco float64

        err = produtosReturn.Scan(&id, &nome, &descricao, &preco, &quantidade)
        if err != nil {
            panic(err.Error())
        }

        p.Nome = nome
        p.Descricao = descricao
        p.Preco = preco
        p.Quantidade = quantidade

        produtos = append(produtos, p)
   }
   
   defer db.Close()
   return produtos
}