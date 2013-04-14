
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHandler;
import org.jnetpcap.PcapIf;

@SuppressWarnings("deprecation")
public class Project3Pcap {

	@SuppressWarnings("deprecation")
  public static void main(String[] args) {
		List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
		StringBuilder errbuf = new StringBuilder(); // For any error msgs

		/***************************************************************************
		 * Get a list of devices on this system
		 **************************************************************************/
		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
			return;
		}
		PcapIf device = alldevs.get(1); // We know we have atleast 1 device

		/***************************************************************************
		 * Open up the selected device
		 **************************************************************************/
		int snaplen = 64 * 1024; // Capture all packets
		int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
		int timeout = 10 * 1000; // 10 seconds in millis
		Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
		if (pcap == null) {
			System.err.printf("Error while opening device for capture: " + errbuf.toString());
			return;
		}
		
		/***************************************************************************
		 * Create a PcapDumper and associate it with the pcap capture (for use with wireshark)
		 ***************************************************************************/
		String ofile = "capture-file.cap";
		PcapDumper dumper = pcap.dumpOpen(ofile); // output file

		/***************************************************************************
		 * Create a packet hander which receive packets and tell the dumper
		 * to write those packets to its output file ("capture-file.cap)
		 **************************************************************************/
		PcapHandler<PcapDumper> dumpHandler = new PcapHandler<PcapDumper>() {

			public void nextPacket(PcapDumper dumper, long seconds, int useconds,
			    int caplen, int len, ByteBuffer buffer) {

				dumper.dump(seconds, useconds, caplen, len, buffer);
			}
		};

		/***************************************************************************
		 * Fifth we enter the loop and tell it to capture X packets (right now 10).
		 **************************************************************************/
		pcap.loop(10, dumpHandler, dumper);
		
		File file = new File(ofile);
		System.out.printf("%s file has %d bytes in it!\n", ofile, file.length());
		

		/*
		 * Last thing to do is close the dumper and pcap handles
		 */
		dumper.close(); // Won't be able to delete without explicit close
		pcap.close();
		
		if (file.exists()) {
			file.delete(); // Cleanup
		}

	}
}