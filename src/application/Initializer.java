package application;

import peersim.edsim.*;
import peersim.core.*;
import peersim.config.*;

/*
  Module d'initialisation de helloWorld: 
  Fonctionnement:
  pour chaque noeud, le module fait le lien entre la couche transport et la couche applicative
  ensuite, il fait envoyer au noeud 0 un message "Hello" a tous les autres noeuds
  */
public class Initializer implements peersim.core.Control
{    
        private int applicationPid;

        public Initializer(String prefix)
        {
                //recuperation du pid de la couche applicative
                this.applicationPid = Configuration.getPid(prefix + ".applicationProtocolPid");
        }

        public boolean execute()
        {
                int nodeNb;
                ApplicationProtocol appProto;
                Node node;

                //recuperation de la taille du reseau
                nodeNb = Network.size();

                if (nodeNb < 1)
                {
                        System.err.println("Network size is not positive");
                        System.exit(1);
                }

                //pour chaque noeud, on fait le lien entre la couche applicative et la couche transport
                for (int i = 0; i < nodeNb; i++)
                {
                        node = Network.get(i);
                        appProto = (ApplicationProtocol)node.getProtocol(this.applicationPid);
                        appProto.init(i);
                }
//                EDSimulator.add(100, new Message(Message.ROLLBACK, "10", 1), Network.get(1), 0);
//                EDSimulator.add(200, new Message(Message.ROLLBACK, "10", 2), Network.get(2), 0);
                        EDSimulator.add(500, new Message(Message.DIE, "<A+ sous l'bus>", -1), Network.get(2), this.applicationPid);

                System.out.println("Initialization completed");
                return false;
        }
}
