package db

import (
	"database/sql"
	"fmt"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

func ConectaComBancoDeDados() *sql.DB {
	conexao := "alifi3988:Familia308@@tcp(127.0.0.1:3306)/loja_virtual?parseTime=true"

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