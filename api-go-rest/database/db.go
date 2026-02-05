package database

import (
	"log"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var (
	DB  *gorm.DB
	err error
)

// Referencia para a conexão com o https://gorm.io/docs/connecting_to_the_database.html
func ComectaComBancoDeDados() {
	dsn := "host=localhost user=root password=root dbname=root port=5432 sslmode=disable TimeZone=Asia/Shanghai"
	DB, err = gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatalln("Erro ao iniciar o Banco de Dados. ", err)
	}
}
