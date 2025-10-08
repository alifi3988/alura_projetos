package controllers

import (
	"html/template"
	"log"
	"net/http"
	"strconv"

	model "loja-virtual/src/main/models"
)

var temp = template.Must(template.ParseGlob("./templates/*.html"))

func Index(w http.ResponseWriter, r *http.Request) {

	produtos := model.BuscaTodosOsProdutos()

	err := temp.ExecuteTemplate(w, "Index", produtos)

	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}

func NewProduct(w http.ResponseWriter, r *http.Request) {
	temp.ExecuteTemplate(w, "NewProduct", nil)
}

func Insert(w http.ResponseWriter, r *http.Request) {
	if r.Method == "POST" {

		//pegando os valores que estão vindo da requisição
		nome := r.FormValue("nome")
		descricao := r.FormValue("descricao")
		preco := r.FormValue("preco")
		quantidade := r.FormValue("quantidade")

		precoConvertido, err := strconv.ParseFloat(preco, 64)

		if err != nil {
			log.Println("Erro: ", err)
		}

		quantidadeConvertido, err := strconv.Atoi(quantidade)

		if err != nil {
			log.Println("Erro: ", err)
		}

		err = model.CriarNovoProduto(nome, descricao, precoConvertido, quantidadeConvertido)

		if err != nil {
			log.Panic("Erro na criação do produto. Valide as informações. /nError: ", err)
		}
		
		http.Redirect(w, r, "/", http.StatusSeeOther)
	}	
	http.Redirect(w,r, "/", http.StatusCreated)
}

func Delete(w http.ResponseWriter, r *http.Request) {

	produtoId := r.URL.Query().Get("id")

	err := model.DeleteProduto(produtoId)

	if err != nil {
		log.Fatal("Erro ao deletar produto. Error: ", err)
	}
	
	http.Redirect(w, r, "/", http.StatusSeeOther)
}
