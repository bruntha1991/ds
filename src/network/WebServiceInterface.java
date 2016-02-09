package network;

/**
 * Created by hiran on 2/7/16.
 */
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface WebServiceInterface {
    @WebMethod void join(String ip, int port);
    @WebMethod void leave(String ip, int port);
    @WebMethod void search(String query, int hops, String ipfrom, int port);
    @WebMethod void result(String ip, int port, String[] files, int hops);
}
