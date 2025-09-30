package main

import (
	"database/sql"
	"fmt"
	"html/template"
	"net/http"

	_ "github.com/lib/pq"
)

type Produto struct {
	Nome       string
	Descricao  string
	Preco      float64
	Quantidade int
}

func conectaComBancoDeDados() *sql.DB {
	conexao := "user=postgres dbname=postgres password=123@123 host=localhost sslmode=disable"

	db, err := sql.Open("postgres", conexao)

	if err != nil {
		fmt.Println("Erro na conexão com o bando de dados!")
		panic(err.Error())
	}

	return db
}

var temp = template.Must(template.ParseGlob("../templates/*.html"))

func main() {

	fmt.Println("INICIO DA CHAMADA DO PROGRAMA.")
	http.HandleFunc("/", index)
	err := http.ListenAndServe(":8000", nil)

	db := conectaComBancoDeDados()
	defer db.Close()

	if err != nil {
		fmt.Println("Erro ao iniciar servidor:", err)
	}

	fmt.Println("FIM DA CHAMADA DO PROGRAMA.")

}

func index(w http.ResponseWriter, r *http.Request) {

	produtos := []Produto{
		{Nome: "Camiseta", Descricao: "Camisa de manga cumprida", Preco: 39.90, Quantidade: 10},
		{"Tenis", "Confortavel", 99.99, 20},
		{"Fone", "Cor preta", 150.99, 5},
		{"Mouse", "Cor preta", 75.99, 15},
	}

	err := temp.ExecuteTemplate(w, "Index", produtos)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
