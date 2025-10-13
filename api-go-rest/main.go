package main

import (
	"fmt"

	"go-rest-api/models"
	"go-rest-api/routes"
)

func main() {


	models.Personalidades =[]models.Personalidade {
		{Id: 1, Nome: "Nome da Personalidade 1", Historia: "ckçdkcnsçknsçfkjaçskfjnsa"},
		{Id: 2, Nome: "Nome da Personalidade 2", Historia: "ckçdkcnsçknsçfkjaçskfjnsa"},
		{Id: 3, Nome: "Nome da Personalidade 3", Historia: "ckçdkcnsçknsçfkjaçskfjnsa"},
	}

	fmt.Println("Iniciando o servidor Go...")
	
	routes.HandleRequest()
}
