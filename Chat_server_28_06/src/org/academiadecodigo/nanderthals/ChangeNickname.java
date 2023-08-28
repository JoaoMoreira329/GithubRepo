package org.academiadecodigo.nanderthals;

import java.util.Scanner;

public class ChangeNickname extends AbstractCommand{


    @Override
    public void execute(Client client, String[] args) {
        String userName = client.getClientName();

        Scanner newNameInput = new Scanner(System.in);
        System.out.println("Put your name ");
        String newName = newNameInput.nextLine();

        client.setClientName(newName);
        System.out.println("Name changed from " + userName + "to " + newName);
    }
}
