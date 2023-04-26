docker pull postgres
docker run -p 5432:5432 --name dentflow-database -e POSTGRES_DB=dentflow -e POSTGRES_PASSWORD=eternal -e POSTGRES_USER=eternal --network dentflow-network postgres