package com.nbhirud.distancevectorroutingalgo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entity0 extends Entity {

    //
    protected int[][] distanceTable = new int[NetworkSimulator.NUMENTITIES][NetworkSimulator.NUMENTITIES];

    public static int INFINITY = 999;

    private int[] neighbour = new int[]{0, 1, 1, 1};

    private int[] dtcost = new int[]{0, 1, 3, 7};

    private int[] dtvector = new int[]{0, 1, 3, 7};
    String fontColor = "red";

    Date dNow = new Date();
    SimpleDateFormat ft
            = new SimpleDateFormat("HH:mm:ss:SS");

    // Perform any necessary initialization in the constructor
    public Entity0() {
        System.out.println("<font color = \"" + fontColor + "\">");
        int i, j;
        System.out.println("Entity0 is called at t = "
                + ft.format(dNow));
        System.out.println("<br>");

        /*
		 * Initialize the distance table, distance table of node 0 looks like
		 * this: 
		 * 	 0 	 1   2   3
		 * 0 0 	 999 999 999 
		 * 1 999 1   999 999 
		 * 2 999 999 3   999 
		 * 3 999 999 999 7
         */
        for (i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            for (j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
                if (((neighbour[i] == 1) && (i == j)) || (i == 0 && j == 0)) {
                    distanceTable[i][j] = dtcost[i];
                } else {
                    distanceTable[i][j] = INFINITY;
                }
            }
        }

        for (i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            int[] temp = new int[NetworkSimulator.NUMENTITIES];
            if (neighbour[i] == 1) {
                for (j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
                    temp[j] = dtvector[j];
                }
                Packet p = new Packet(0, i, temp);
                NetworkSimulator.toLayer2(p);
            }
        }

        System.out.println("</font>");
    }

    // Handle updates when a packet is received. Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update. Be careful to construct the source and destination of
    // the packet correctly. Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p) {
        System.out.println("<font color = \"" + fontColor + "\">");
        System.out.println("rtupdate0 is called at t= "
                + ft.format(dNow));
        System.out.println("<br>");
        System.out.println("node 0 is receiving packet from node "
                + p.getSource());
        System.out.println("<br>");
        int i, j;
        int tempCost = 0;
        int vectorChange = 0;
        for (i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (i != 0) {
                tempCost = dtcost[p.getSource()] + p.getMincost(i);
                if (tempCost < distanceTable[i][p.getSource()]) {
                    distanceTable[i][p.getSource()] = tempCost;
                    System.out.println("Node 0's distance table updated.");
                    System.out.println("<br>");
                    printDT();
                }
                if (tempCost < dtvector[i]) {
                    vectorChange = 1;
                    dtvector[i] = tempCost;
                }
            }
        }

        if (vectorChange == 1) {

            for (i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
                int[] temp = new int[NetworkSimulator.NUMENTITIES];

                if (neighbour[i] == 1) {
                    // temp.sourceid = 0;
                    // /temp.destid = i;
                    for (j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
                        temp[j] = dtvector[j];
                    }
                    p = new Packet(0, i, temp);
                    System.out.println("Due to vector change, Packet is being sent to node " + i
                            + " from Node 0.");
                    System.out.println("<br>");
                    NetworkSimulator.toLayer2(p);
                    System.out.println("<br>");
                }
            }
            System.out.println();
            System.out.println("<br>");
        }
        System.out.println("</font>");

    }

    public void linkCostChangeHandler(int whichLink, int newCost) {
    }

    public void printDT() {
        System.out.println("<font color = \"" + fontColor + "\">");

        System.out.println("<table border=\"1\" class = \"entity1_red\">");
        System.out.println();
        //System.out.println("<br>");
        System.out.println("<tr><td></d><td colspan = 3 align=\"center\">via</td></tr>");
        //System.out.println("<br>");
        System.out.println("<tr><th>D0</th><th>1</th><th>2</th><th>3</th></tr>");
        //System.out.println("<br>");
        //System.out.println("----+------------");
        //System.out.println("<br>");

        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++) {
            System.out.print("<tr><th>" + i + "</th>");

            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++) {
                /*
				if (distanceTable[i][j] < 10) {
					System.out.print("&nbsp;&nbsp;&nbsp;");
				} else if (distanceTable[i][j] < 100) {
					System.out.print("&nbsp;&nbsp;");
				} else {
					System.out.print("&nbsp;");
				}
                 */
                System.out.print("<td>" + distanceTable[i][j] + "</td>");
            }
            System.out.println();
            //System.out.println("<br>");
            System.out.print("</tr>");
        }

        System.out.println("<table>");
        System.out.println("</font>");

        /*
                System.out.println("<font color = \""+fontColor+"\">");
                		System.out.println();
                System.out.println("<br>");
		System.out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;via");
                System.out.println("<br>");
		System.out.println("&nbsp;D0&nbsp;|&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;3");
                System.out.println("<br>");
                		System.out.println("----+------------");
                System.out.println("<br>");
                
		for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++) {
			System.out.print("   " + i + "|");
			for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++) {
				if (distanceTable[i][j] < 10) {
					System.out.print("&nbsp;&nbsp;&nbsp;");
				} else if (distanceTable[i][j] < 100) {
					System.out.print("&nbsp;&nbsp;");
				} else {
					System.out.print("&nbsp;");
				}

				System.out.print(distanceTable[i][j]);
			}
			System.out.println();
                        System.out.println("<br>");
                        
		}
                System.out.println("</font>");
                
                
         */
    }
}
