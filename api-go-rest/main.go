package main

import (
	"fmt"

	"go-rest-api/database"
	"go-rest-api/routes"
)

func main() {
	fmt.Println("Iniciando o servidor BD...")
	database.ComectaComBancoDeDados()

	fmt.Println("Iniciando o servidor Go...")
	routes.HandleRequest()
}
