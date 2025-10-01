package main

import (
	"database/sql"
	"fmt"
	"html/template"
	"net/http"
	"log"

	_ "github.com/lib/pq"
	_ "github.com/go-sql-driver/mysql"
)

type Produto struct {
    Id         int
	Nome       string
	Descricao  string
	Preco      float64
	Quantidade int
}

func conectaComBancoDeDados() *sql.DB {
	conexao := "****:****@tcp(127.0.0.1:3306)/loja_virtual?parseTime=true"

	db, err := sql.Open("mysql", conexao)
	if err != nil {
		log.Fatal("❌ Erro ao abrir conexão:", err)
	}

	if err := db.Ping(); err != nil {
		log.Fatal("❌ Erro ao conectar no banco:", err)
	}

	fmt.Println("✅ Conectado ao banco com sucesso!")
	return db
}

var temp = template.Must(template.ParseGlob("../templates/*.html"))

func main() {
	fmt.Println("✅ Iniciando o template do programa...")
	http.HandleFunc("/", index)
	fmt.Println("✅ Iniciando programa em: https://localhost:8000")
	err := http.ListenAndServe(":8000", nil)

	if err != nil {
		fmt.Println("❌ Erro ao iniciar servidor:", err)
	}

	fmt.Println("✅ Finalizando o programa...")

}

func index(w http.ResponseWriter, r *http.Request) {

   fmt.Println("✅ Iniciando o servidor de banco de dados...")
   db := conectaComBancoDeDados()

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

	err = temp.ExecuteTemplate(w, "Index", produtos)
	defer db.Close()

	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

}
