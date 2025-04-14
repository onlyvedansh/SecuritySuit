from scapy.all import sniff, ARP
import datetime

def detect_scan(packet):
    if packet.haslayer(ARP):  # Check if the packet is an ARP request
        if packet.op == 1:  # ARP request (who-has)
            timestamp = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            print(f"[{timestamp}] Detected ARP scan from: {packet.psrc} ({packet.hwsrc})")
            with open("network_scan_log.txt", "a") as log_file:
                log_file.write(f"[{timestamp}] Detected ARP scan from: {packet.psrc} ({packet.hwsrc})\n")

print("Listening for network scans...")
sniff(prn=detect_scan, store=False, filter="arp")