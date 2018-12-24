package com.github.tax1driver.sectormanager.helpers;

import com.github.tax1driver.sectormanager.objects.networking.Connection;
import com.github.tax1driver.sectormanager.objects.representative.Client;

import java.util.List;

public class ClientManager {
    private static List<Client> clients;

    public static List<Client> getClients()
    {
        return clients;
    }

    public static Client getClientByConnection(Connection c)
    {
        for (Client client : clients) {
            if (client.getConnection() == c)
                return client;
        }

        return null;
    }

    public static void addClient(Client client) {
        clients.add(client);
        //TODO: handle
    }

}
