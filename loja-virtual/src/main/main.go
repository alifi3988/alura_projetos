package main

import (
	"fmt"
	"net/http"
	"loja-virtual/src/main/routes"

	_ "github.com/lib/pq"
	_ "github.com/go-sql-driver/mysql"	
)

func main() {
	fmt.Println("✅ Iniciando o template do programa...")
	routes.CarregaRotas()

	fmt.Println("✅ Iniciando programa em: https://localhost:8000")
	err := http.ListenAndServe(":8000", nil)

	if err != nil {
		fmt.Println("❌ Erro ao iniciar servidor:", err)
	}

	fmt.Println("✅ Finalizando o programa...")
}
