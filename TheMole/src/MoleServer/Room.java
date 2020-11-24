package MoleServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/*public class Room {
	public static final LinkedList<ChannelGroup> roomList = new LinkedList<ChannelGroup>();
	public static final LinkedList<String> roomHostUser = new LinkedList<String>();

	public static void roomCreat(ChannelHandlerContext ctx, String roomHost) {
		Channel Host = ctx.channel();
		ChannelGroup room = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
		room.add(Host);
		roomList.add(room);
		roomHostUser.add(roomHost);
		System.out.println(roomHostUser);
		ctx.writeAndFlush("CREAT," + roomHost);
	}

	public static void roomJoin(ChannelHandlerContext ctx, String roomHostName, String myName) {
		Channel guest = ctx.channel();
		int index = Collections.binarySearch(roomHostUser, roomHostName);
		System.out.println(index);
		if (roomList.get(index).size() > 1) 
			ctx.writeAndFlush("FULL");
		else {
		roomList.get(index).add(guest);
		for (Channel channel : roomList.get(index)) {
			if(channel == guest)
				guest.writeAndFlush("JOIN," + roomHostName + "," + myName);
			else
				channel.writeAndFlush("GUEST," + roomHostName + "," + myName);
			}
		}
	}
	
	public static void roomListSend(ChannelHandlerContext ctx) {
		ctx.write("ROOMLIST,");
		for(int i = 0; i < roomHostUser.size(); i++) 
			ctx.write(roomHostUser.get(i) + ",");
		ctx.flush();
	}
	public static void roomListRefresh(ChannelHandlerContext ctx) {
		ctx.write("REFRESH,");
		for(int i = 0; i < roomHostUser.size(); i++) 
			ctx.write(roomHostUser.get(i) + ",");
		ctx.flush();
	}
	public static void roomDelete(ChannelHandlerContext ctx, String hostName) {
		Channel me = ctx.channel();
		int index = Collections.binarySearch(roomHostUser, hostName);
		System.out.println(index);
		roomHostUser.remove(hostName);
		System.out.println(roomHostUser);
		for (Channel channel : roomList.get(index)) {
			if(channel == me) {
				me.write("REFRESH,");
				for(int i = 0; i < roomHostUser.size(); i++) 
					me.write(roomHostUser.get(i) + ",");
				me.flush();
			} else {
				channel.write("BOOM,");
				for(int i = 0; i < roomHostUser.size(); i++) 
					channel.write(roomHostUser.get(i) + ",");
				channel.flush();
			}
		}
		roomList.remove(index);
	}
	public static void roomOut(ChannelHandlerContext ctx, String hostName) {
		Channel guest = ctx.channel();
		int index = Collections.binarySearch(roomHostUser, hostName);
		roomList.get(index).remove(guest);
		ctx.write("REFRESH,");
		for(int i = 0; i < roomHostUser.size(); i++) 
			ctx.write(roomHostUser.get(i) + ",");
		ctx.flush();
	}
	public static void readyState(ChannelHandlerContext ctx, String m, String hostName) {
		Channel guest = ctx.channel();
		int index = Collections.binarySearch(roomHostUser, hostName);
		if (m.equals("[READY]")) { 
			for (Channel channel : roomList.get(index)) {
				if(channel != guest)
					channel.writeAndFlush("READY");
			}
		} else { 
			for (Channel channel : roomList.get(index)) {
				if(channel != guest)
					channel.writeAndFlush("CANSLE");
			}
		}
	}
	public static void roomChatting(ChannelHandlerContext ctx, String message, String name, String hostName) {
		Channel host = ctx.channel();
		int index = Collections.binarySearch(roomHostUser, hostName);
		for (Channel channel : roomList.get(index)) {
			channel.writeAndFlush("SENDMESSAGE," + name + ": " + message);
		}
	}
	public static void startGame(ChannelHandlerContext ctx, String hostName, String guestName) {
		Channel host = ctx.channel();
		int index = Collections.binarySearch(roomHostUser, hostName);
		int r = (int)(Math.random()*2);
		for (Channel channel : roomList.get(index)) {
			if (r == 0 && channel != host) {
				host.writeAndFlush("MOLESTART," + hostName + "," + "," + guestName + "," + hostName + "," + guestName);
				channel.writeAndFlush("HUMANSTART,"  + hostName + "," + "," + guestName + "," + guestName + "," + hostName);
			} 
			else if (r == 1 && channel != host) {
				host.writeAndFlush("HUMANSTART,"  + hostName + "," + "," + guestName + "," + hostName + "," + guestName);
				channel.writeAndFlush("MOLESTART,"  + hostName + "," + "," + guestName + "," + guestName + "," + hostName);
			}
		}
	}
}*/

public class Room {
	public static final HashMap<String, ChannelGroup> roomManager = new HashMap<String, ChannelGroup>();

	public static void roomCreat(ChannelHandlerContext ctx, String roomHost) {
		Channel host = ctx.channel();
		ChannelGroup room = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
		room.add(host);
		roomManager.put(roomHost, room);
		ctx.writeAndFlush("CREAT," + roomHost);
		System.out.println(roomManager);
	}

	public static void roomJoin(ChannelHandlerContext ctx, String roomHostName, String myName) {
		Channel guest = ctx.channel();
		
		if (roomManager.get(roomHostName).size() > 1)
			ctx.writeAndFlush("FULL");
		else {
			roomManager.get(roomHostName).add(guest);
			for (Channel channel : roomManager.get(roomHostName)) {
				if(channel == guest)
					guest.writeAndFlush("JOIN," + roomHostName + "," + myName);
				else
					channel.writeAndFlush("GUEST," + roomHostName + "," + myName);
			}
		}
		System.out.println(roomManager);
	}
	
	public static void roomListSend(ChannelHandlerContext ctx) {
		ctx.write("ROOMLIST,");
		for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
			ctx.write(entry.getKey() + ",");
		ctx.flush();
	}
	public static void roomListRefresh(ChannelHandlerContext ctx) {		
		ctx.write("REFRESH,");
		for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
			ctx.write(entry.getKey() + ",");
		ctx.flush();
	}
	public static void roomDelete(ChannelHandlerContext ctx, String hostName) {
		Channel me = ctx.channel();
		
		for (Channel channel : roomManager.get(hostName)) {
			roomManager.remove(hostName);
			if(channel == me) {
				me.write("REFRESH,");
				for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
					me.write(entry.getKey() + ",");
				me.flush();
			} else {
				channel.write("BOOM,");
				for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
					channel.write(entry.getKey() + ",");
				channel.flush();
			}
		}
	}
	public static void roomOut(ChannelHandlerContext ctx, String hostName) {
		Channel guest = ctx.channel();

		roomManager.get(hostName).remove(guest);
		ctx.write("REFRESH,");
		for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
			ctx.write(entry.getKey() + ",");
		ctx.flush();
		
		for (Channel channel : roomManager.get(hostName)) {
			if(channel != guest)
				channel.writeAndFlush("GUESTOUT," + hostName);
		}
	}
	public static void readyState(ChannelHandlerContext ctx, String m, String hostName) {
		Channel guest = ctx.channel();
		
		if (m.equals("[READY]")) {
			for (Channel channel : roomManager.get(hostName)) {
				if(channel != guest)
					channel.writeAndFlush("READY");
			}
		} else {
			for (Channel channel : roomManager.get(hostName)) {
				if(channel != guest)
					channel.writeAndFlush("CANSLE");
			}
		}
	}
	public static void roomChatting(ChannelHandlerContext ctx, String message, String name, String hostName) {
		Channel host = ctx.channel();
	
		for (Channel channel : roomManager.get(hostName))
			channel.writeAndFlush("SENDMESSAGE," + name + ": " + message);
	}
	public static void startGame(ChannelHandlerContext ctx, String hostName, String guestName) {
		Channel host = ctx.channel();
		int r = (int)(Math.random()*2);
		
		for (Channel channel : roomManager.get(hostName)) {
			if (r == 0 && channel != host) {
				host.writeAndFlush("MOLESTART," + hostName + "," + "," + guestName + "," + hostName + "," + guestName);
				channel.writeAndFlush("HUMANSTART,"  + hostName + "," + "," + guestName + "," + guestName + "," + hostName);
			} 
			else if (r == 1 && channel != host) {
				host.writeAndFlush("HUMANSTART,"  + hostName + "," + "," + guestName + "," + hostName + "," + guestName);
				channel.writeAndFlush("MOLESTART,"  + hostName + "," + "," + guestName + "," + guestName + "," + hostName);
			}
		}
	}
}

