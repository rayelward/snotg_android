package edu.depaul.snotg_android.Chat;

import java.util.*;
import java.io.*;
 
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

import android.util.Log;
 
public class JabberSmackAPI implements MessageListener{
 
    XMPPConnection connection;
    private XMPPClient xmppClient;
    
    public JabberSmackAPI (XMPPClient xmppClient) {
        super();
        this.xmppClient = xmppClient;
        
    }
    public void login(String userName, String password) throws XMPPException
    {
    ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com",5222, "gmail.com");
    config.setCompressionEnabled(true);
    config.setSASLAuthenticationEnabled(false);
    connection = new XMPPConnection(config);
    connection.connect();
    connection.login(userName, password);
	setConnection(connection);
    }
 
    public void sendMessage(String message, String to) throws XMPPException
    {
    Chat chat = connection.getChatManager().createChat(to, this);
    chat.sendMessage(message);
    }
 
    public void disconnect()
    {
    connection.disconnect();
    }
 
    public void processMessage(Chat chat, Message message)
    {
    //if(message.getType() == Message.Type.chat)
    	//xmppClient.getMessage(message.getBody(),message.getFrom());
    }
    public void setConnection (XMPPConnection connection) 
    {
	    this.connection = connection;
		if (connection != null) 
		{
		    // Add a packet listener to get messages sent to us
		    PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		    connection.addPacketListener(new PacketListener() 
		    {
		        public void processPacket(Packet packet) 
		        {
		            Message message = (Message) packet;
		            if (message.getBody() != null) 
		            {
		                String fromName = StringUtils.parseBareAddress(message.getFrom());
		                Log.i("XMPPClient", "Got text [" + message.getBody() + "] from [" + fromName + "]");
		                xmppClient.getMessage(message.getBody(),fromName);
		            }
		        }
		        
		    }, filter);
		
		}
	}
}
