package network;

/**
 * Created by hiran on 2/7/16.
 */
import javax.jws.WebService;

@WebService(endpointInterface = "network.WebServiceInterface")
public class WebServiceController implements WebServiceInterface {
    public WebServiceController() {
    }

    Node myNode;
    public WebServiceController(Node node){
        this.myNode = node;
    }

    @Override
    public void join(String ip, int port) {
        Message msg=new JOINMessage(ip, port);
        System.out.println("JOIN message received: "+ip+":"+port);
        myNode.onMessageReceived(msg);
    }

    @Override
    public void leave(String ip, int port) {
        Message msg=new LEAVEMessage(ip, port);
        System.out.println("LEAVE message received: "+ip+":"+port);
        myNode.onMessageReceived(msg);
    }

    @Override
    public void search(String query, int hops, String ipfrom, int port){
        Message msg=new SERMessage(query, hops, ipfrom, port);
        System.out.println("SEARCH message received: "+ipfrom+":"+port+" for query: "+ query);
        myNode.onMessageReceived(msg);

    }

    @Override
    public void result(String ip, int port, String[] files, int hops) {
        Message msg = new SEROKMessage(ip,port,files, hops);
        System.out.println("SEROK message received: "+msg);
        myNode.onMessageReceived(msg);
    }
}
