package network;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Created by hiran on 1/10/16.
 */
public class MessageTransfer {

    //Receiver messageReceiver;
    Sender messageSender;
    Node myNode;

    public MessageTransfer(Node myNode){


        this.myNode = myNode;
        Endpoint.publish("http://" + Configuration.getMyIpAddress() + ":" + Configuration.getMyPortNumber() + "/ws/node", new WebServiceController(myNode));


//        try{
//            messageReceiver = new Receiver(Configuration.getMyPortNumber(),this);
//            messageReceiver.start();
//        }catch(IOException ex){
//
//        }
       messageSender = new Sender();

    }

    public void sendMessage(Message message){
        System.out.println(message);
            if(message.msgType == MessageType.REG || message.msgType == MessageType.UNREG){

                //System.out.print(message.ip_to +" "+message.port_to);
                char[] respond = messageSender.sendTCP(message.toString(), message.ip_to, message.port_to);
                String msg = new String(respond);

                this.translateMessage(msg.trim());

            }else{
                try{
                    URL url = new URL("http://"+message.ip_to+":"+message.port_to+"/ws/node?wsdl");

                    QName qname = new QName("http://network/", "WebServiceControllerService");
                    Service service = Service.create(url, qname);
                    WebServiceInterface webSer = service.getPort(WebServiceInterface.class);

                    if(message.msgType==MessageType.JOIN){
                        webSer.join(message.ip_from, message.port_from);
                    }else if(message.msgType==MessageType.LEAVE){
                        webSer.leave(message.ip_from, message.port_from);
                    }else if(message.msgType==MessageType.SER){
                        webSer.search(message.query, message.hops, message.ip_from, message.port_from);
                    }else if(message.msgType==MessageType.SEROK){
                        webSer.result(message.ip_from, message.port_from, message.files,message.hops);
                    }
                } catch (Exception e) {
                    Configuration.removeNeighbor(message.ip_to, message.port_to);
                }
            }




    }

    public void translateMessage(String msg){

        System.out.println("RECEIVED MESSAGE: "+ msg);
        String[] msg_data = msg.split(" ");

        Message translatedMsg;

        if(msg_data[1].equals("REGOK")){

            translatedMsg = new REGOKMessage(msg);
            myNode.onMessageReceived(translatedMsg);

        }else if(msg_data[1].equals("UNROK")){

            translatedMsg = new UNREGOKMessage(msg);
            myNode.onMessageReceived(translatedMsg);

        }else if(msg_data[1].equals("JOINOK")){

            translatedMsg = new JOINOKMessage(msg);
            myNode.onMessageReceived(translatedMsg);

        }else if(msg_data[1].equals("LEAVEOK")){

            translatedMsg = new LEAVEOKMessage(msg);
            myNode.onMessageReceived(translatedMsg);

        }else if(msg_data[1].equals("JOIN")){

            translatedMsg = new JOINMessage(msg_data[2], Integer.parseInt(msg_data[3]));
            myNode.onMessageReceived(translatedMsg);

        }else if(msg_data[1].equals("SER")){

            translatedMsg = new SERMessage(msg_data[4], Integer.parseInt(msg_data[5]), msg_data[2], Integer.parseInt(msg_data[3]));
            myNode.onMessageReceived(translatedMsg);

        }else if(msg_data[1].equals("LEAVE")){

            translatedMsg = new LEAVEMessage(msg_data[2], Integer.parseInt(msg_data[3]));
            myNode.onMessageReceived(translatedMsg);

        }else if(msg_data[1].equals("SEROK")){

            translatedMsg = new SEROKMessage(msg);
            myNode.onMessageReceived(translatedMsg);

        }else{
            translatedMsg = null;
            myNode.onMessageReceived(translatedMsg);
        }

    }
}
enum MessageType { REG, REGOK, UNROK, UNREG, JOINOK , JOIN, LEAVE, LEAVEOK, SER, SEROK};
