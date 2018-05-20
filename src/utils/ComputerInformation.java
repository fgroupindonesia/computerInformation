package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * these code was written at 02:54 pm, 20th may 2018.
 * @author @FgroupIndonesia.com
 */
public class ComputerInformation {

    
    private String machineID;
    private String hostName;
    private String osName;
    private String osVersion;
    private String osManufacturer;
    private String osConfiguration;
    private String osBuildType;
    private String regOwner;
    private String regOrganization;
    private String productID;
    private String originalInstallDate;
    private String systemBootTime;
    private String systemManufacturer;
    private String systemType;
    private String systemModel;
    private String processor;
    private String biosVersion;
    private String windowsDir;
    private String systemDir;
    private String bootDevice;
    private String systemLocale;
    private String inputLocale;
    private String timeZone;
    private String totalPhysMemory;
    private String availablePhysMemory;
    private String virtualMemMaxSize;
    private String virtualMemAvailable;
    private String virtualMemInUse;
    private String pageFileLocation;
    private String domain;
    private String logonServer;
    private String hotfix;
    private String networkCard;

    /**
     * @return the machineID
     */
    public String getMachineID() {
        return machineID;
    }

    /**
     * @param machineID the machineID to set
     */
    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public ComputerInformation() {

        String osName = System.getProperty("os.name");

        if (osName.toLowerCase().contains("windows")) {
            // #1 work: setting machineID (wmic.exe)
            prepareWmic();

            // #2 work: setting other variables (systeminfo.exe)
            prepareSystemInfo();
        } else {
            System.out.println("unsupported operating system!");
            System.out.println("- please contact our administrator to upload the latest version library for this " + osName + " usage -");
        }

    }

    private void prepareWmic() {
        try {
            ProcessBuilder procBuild = new ProcessBuilder("wmic.exe", "csproduct", "get", "UUID");
            Process proc = procBuild.start();

            BufferedReader buff = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String founded = null;
            int count = 1;

            while ((founded = buff.readLine()) != null) {
                if (founded.length() > 0) {
                    founded = founded.replace("UUID", "").replaceAll("\\s+", "").replaceAll("\\n", "");
                    // we take the second output
                    // coz the 1st output is invalid empty character
                    if (count == 2) {
                        //System.out.println(founded);
                        setMachineID(founded);
                    }

                    count++;
                }

            }

            buff.close();

        } catch (Exception ex) {
            System.out.println("Error when calling prepareWmic() " + ex.getMessage());
        }

    }

    String prevKey = null;

    private void prepareSystemInfo() {
        try {
            ProcessBuilder procBuild = new ProcessBuilder("systeminfo.exe");
            Process proc = procBuild.start();

            BufferedReader buff = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String founded = null;

            int count = 1;

            while ((founded = buff.readLine()) != null) {
                if (founded.length() > 0) {
                    sortData(founded);
                    count++;
                }
            }

            //System.out.println(this.getOsName());
            buff.close();

        } catch (Exception ex) {
            System.out.println("Error when calling prepareSystemInfo() " + ex.getMessage());
        }
    }

    private void sortData(String rawDataByLine) {
        if (rawDataByLine.contains(KeyRef.HOST_NAME)) {
            setHostName(getSystemInfoValue(KeyRef.HOST_NAME, rawDataByLine));
            prevKey = KeyRef.HOST_NAME;
        } else if (rawDataByLine.contains(KeyRef.OS_NAME)) {
            setOsName(getSystemInfoValue(KeyRef.OS_NAME, rawDataByLine));
            prevKey = KeyRef.OS_NAME;
        } else if (rawDataByLine.contains(KeyRef.OS_VERSION)) {
            setOsVersion(getSystemInfoValue(KeyRef.OS_VERSION, rawDataByLine));
            prevKey = KeyRef.OS_VERSION;
        } else if (rawDataByLine.contains(KeyRef.OS_MANUFACTURER)) {
            setOsManufacturer(getSystemInfoValue(KeyRef.OS_MANUFACTURER, rawDataByLine));
            prevKey = KeyRef.OS_MANUFACTURER;
        } else if (rawDataByLine.contains(KeyRef.OS_CONFIGURATION)) {
            setOsConfiguration(getSystemInfoValue(KeyRef.OS_CONFIGURATION, rawDataByLine));
            prevKey = KeyRef.OS_CONFIGURATION;
        } else if (rawDataByLine.contains(KeyRef.OS_BUILD_TYPE)) {
            setOsBuildType(getSystemInfoValue(KeyRef.OS_BUILD_TYPE, rawDataByLine));
            prevKey = KeyRef.OS_BUILD_TYPE;
        } else if (rawDataByLine.contains(KeyRef.REGISTERED_OWNER)) {
            setRegOwner(getSystemInfoValue(KeyRef.REGISTERED_OWNER, rawDataByLine));
            prevKey = KeyRef.REGISTERED_OWNER;
        } else if (rawDataByLine.contains(KeyRef.REGISTERED_ORGANIZATION)) {
            setRegOrganization(getSystemInfoValue(KeyRef.REGISTERED_ORGANIZATION, rawDataByLine));
            prevKey = KeyRef.REGISTERED_ORGANIZATION;
        } else if (rawDataByLine.contains(KeyRef.PRODUCT_ID)) {
            setProductID(getSystemInfoValue(KeyRef.PRODUCT_ID, rawDataByLine));
            prevKey = KeyRef.PRODUCT_ID;
        } else if (rawDataByLine.contains(KeyRef.ORIGINAL_INSTALL_DATE)) {
            setOriginalInstallDate(getSystemInfoValue(KeyRef.ORIGINAL_INSTALL_DATE, rawDataByLine));
            prevKey = KeyRef.ORIGINAL_INSTALL_DATE;
        } else if (rawDataByLine.contains(KeyRef.SYSTEM_BOOT_TIME)) {
            setSystemBootTime(getSystemInfoValue(KeyRef.SYSTEM_BOOT_TIME, rawDataByLine));
            prevKey = KeyRef.SYSTEM_BOOT_TIME;
        } else if (rawDataByLine.contains(KeyRef.SYSTEM_MANUFACTURER)) {
            setSystemManufacturer(getSystemInfoValue(KeyRef.SYSTEM_MANUFACTURER, rawDataByLine));
            prevKey = KeyRef.SYSTEM_MANUFACTURER;
        } else if (rawDataByLine.contains(KeyRef.SYSTEM_MODEL)) {
            setSystemModel(getSystemInfoValue(KeyRef.SYSTEM_MODEL, rawDataByLine));
            prevKey = KeyRef.SYSTEM_MODEL;
        } else if (rawDataByLine.contains(KeyRef.SYSTEM_TYPE)) {
            setSystemType(getSystemInfoValue(KeyRef.SYSTEM_TYPE, rawDataByLine));
            prevKey = KeyRef.SYSTEM_TYPE;
        } else if (rawDataByLine.contains(KeyRef.PROCESSOR)) {
            setProcessor(getSystemInfoValue(KeyRef.PROCESSOR, rawDataByLine));
            prevKey = KeyRef.PROCESSOR;
        } else if (rawDataByLine.contains(KeyRef.BIOS_VERSION)) {
            setBiosVersion(getSystemInfoValue(KeyRef.BIOS_VERSION, rawDataByLine));
            prevKey = KeyRef.BIOS_VERSION;
        } else if (rawDataByLine.contains(KeyRef.WINDOWS_DIR)) {
            setWindowsDir(getSystemInfoValue(KeyRef.WINDOWS_DIR, rawDataByLine));
            prevKey = KeyRef.WINDOWS_DIR;
        } else if (rawDataByLine.contains(KeyRef.SYSTEM_DIR)) {
            setSystemDir(getSystemInfoValue(KeyRef.SYSTEM_DIR, rawDataByLine));
            prevKey = KeyRef.SYSTEM_DIR;
        } else if (rawDataByLine.contains(KeyRef.BOOT_DEVICE)) {
            setBootDevice(getSystemInfoValue(KeyRef.BOOT_DEVICE, rawDataByLine));
            prevKey = KeyRef.BOOT_DEVICE;
        } else if (rawDataByLine.contains(KeyRef.SYSTEM_LOCALE)) {
            setSystemLocale(getSystemInfoValue(KeyRef.SYSTEM_LOCALE, rawDataByLine));
            prevKey = KeyRef.SYSTEM_LOCALE;
        } else if (rawDataByLine.contains(KeyRef.INPUT_LOCALE)) {
            setInputLocale(getSystemInfoValue(KeyRef.INPUT_LOCALE, rawDataByLine));
            prevKey = KeyRef.INPUT_LOCALE;
        } else if (rawDataByLine.contains(KeyRef.TIME_ZONE)) {
            setTimeZone(getSystemInfoValue(KeyRef.TIME_ZONE, rawDataByLine));
            prevKey = KeyRef.TIME_ZONE;
        } else if (rawDataByLine.contains(KeyRef.TOTAL_PHYSICAL_MEM)) {
            setTotalPhysMemory(getSystemInfoValue(KeyRef.TOTAL_PHYSICAL_MEM, rawDataByLine));
            prevKey = KeyRef.TOTAL_PHYSICAL_MEM;
        } else if (rawDataByLine.contains(KeyRef.AVAILABLE_PHYSICAL_MEM)) {
            setAvailablePhysMemory(getSystemInfoValue(KeyRef.AVAILABLE_PHYSICAL_MEM, rawDataByLine));
            prevKey = KeyRef.AVAILABLE_PHYSICAL_MEM;
        } else if (rawDataByLine.contains(KeyRef.VIRTUAL_MEM_MAX_SIZE)) {
            setVirtualMemMaxSize(getSystemInfoValue(KeyRef.VIRTUAL_MEM_MAX_SIZE, rawDataByLine));
            prevKey = KeyRef.VIRTUAL_MEM_MAX_SIZE;
        } else if (rawDataByLine.contains(KeyRef.VIRTUAL_MEM_IN_USE)) {
            setVirtualMemInUse(getSystemInfoValue(KeyRef.VIRTUAL_MEM_IN_USE, rawDataByLine));
            prevKey = KeyRef.VIRTUAL_MEM_IN_USE;
        } else if (rawDataByLine.contains(KeyRef.VIRTUAL_MEM_AVAILABLE)) {
            setVirtualMemAvailable(getSystemInfoValue(KeyRef.VIRTUAL_MEM_AVAILABLE, rawDataByLine));
            prevKey = KeyRef.VIRTUAL_MEM_AVAILABLE;
        } else if (rawDataByLine.contains(KeyRef.PAGE_FILE_LOCATION)) {
            setPageFileLocation(getSystemInfoValue(KeyRef.PAGE_FILE_LOCATION, rawDataByLine));
            prevKey = KeyRef.PAGE_FILE_LOCATION;
        } else if (rawDataByLine.contains(KeyRef.DOMAIN)) {
            setDomain(getSystemInfoValue(KeyRef.DOMAIN, rawDataByLine));
            prevKey = KeyRef.DOMAIN;
        } else if (rawDataByLine.contains(KeyRef.LOGON_SERVER)) {
            setLogonServer(getSystemInfoValue(KeyRef.LOGON_SERVER, rawDataByLine));
            prevKey = KeyRef.LOGON_SERVER;
        } else if (rawDataByLine.contains(KeyRef.HOTFIX)) {
            setHotfix(getSystemInfoValue(KeyRef.HOTFIX, rawDataByLine));
            prevKey = KeyRef.HOTFIX;
        } else if (rawDataByLine.contains(KeyRef.NETWORK_CARD)) {
            setNetworkCard(getSystemInfoValue(KeyRef.NETWORK_CARD, rawDataByLine));
            prevKey = KeyRef.NETWORK_CARD;
        } else if (prevKey.equalsIgnoreCase(KeyRef.PROCESSOR)) {
            // when all of the keys are not matched            
            // adding some entries
            setProcessor(rawDataByLine.trim());
        } else if (prevKey.equalsIgnoreCase(KeyRef.PAGE_FILE_LOCATION)) {
            setPageFileLocation(rawDataByLine.trim());
        } else if (prevKey.equalsIgnoreCase(KeyRef.HOTFIX)) {
            setHotfix(rawDataByLine.trim());
        } else if (prevKey.equalsIgnoreCase(KeyRef.NETWORK_CARD)) {
            setNetworkCard(rawDataByLine.trim());
        }
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the osName
     */
    public String getOsName() {
        return osName;
    }

    /**
     * @param osName the osName to set
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    /**
     * @return the osVersion
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * @param osVersion the osVersion to set
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * @return the osManufacturer
     */
    public String getOsManufacturer() {
        return osManufacturer;
    }

    /**
     * @param osManufacturer the osManufacturer to set
     */
    public void setOsManufacturer(String osManufacturer) {
        this.osManufacturer = osManufacturer;
    }

    /**
     * @return the osConfiguration
     */
    public String getOsConfiguration() {
        return osConfiguration;
    }

    /**
     * @param osConfiguration the osConfiguration to set
     */
    public void setOsConfiguration(String osConfiguration) {
        this.osConfiguration = osConfiguration;
    }

    /**
     * @return the osBuildType
     */
    public String getOsBuildType() {
        return osBuildType;
    }

    /**
     * @param osBuildType the osBuildType to set
     */
    public void setOsBuildType(String osBuildType) {
        this.osBuildType = osBuildType;
    }

    /**
     * @return the regOwner
     */
    public String getRegOwner() {
        return regOwner;
    }

    /**
     * @param regOwner the regOwner to set
     */
    public void setRegOwner(String regOwner) {
        this.regOwner = regOwner;
    }

    /**
     * @return the regOrganization
     */
    public String getRegOrganization() {
        return regOrganization;
    }

    /**
     * @param regOrganization the regOrganization to set
     */
    public void setRegOrganization(String regOrganization) {
        this.regOrganization = regOrganization;
    }

    /**
     * @return the productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return the originalInstallDate
     */
    public String getOriginalInstallDate() {
        return originalInstallDate;
    }

    /**
     * @param originalInstallDate the originalInstallDate to set
     */
    public void setOriginalInstallDate(String originalInstallDate) {
        this.originalInstallDate = originalInstallDate;
    }

    /**
     * @return the systemBootTime
     */
    public String getSystemBootTime() {
        return systemBootTime;
    }

    /**
     * @param systemBootTime the systemBootTime to set
     */
    public void setSystemBootTime(String systemBootTime) {
        this.systemBootTime = systemBootTime;
    }

    /**
     * @return the systemManufacturer
     */
    public String getSystemManufacturer() {
        return systemManufacturer;
    }

    /**
     * @param systemManufacturer the systemManufacturer to set
     */
    public void setSystemManufacturer(String systemManufacturer) {
        this.systemManufacturer = systemManufacturer;
    }

    /**
     * @return the systemType
     */
    public String getSystemType() {
        return systemType;
    }

    /**
     * @param systemType the systemType to set
     */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    /**
     * @return the systemModel
     */
    public String getSystemModel() {
        return systemModel;
    }

    /**
     * @param systemModel the systemModel to set
     */
    public void setSystemModel(String systemModel) {
        this.systemModel = systemModel;
    }

    /**
     * @return the processor
     */
    public String getProcessor() {
        return processor;
    }

    /**
     * @param processor the processor to set
     */
    public void setProcessor(String processor) {
        if (this.processor == null) {
            this.processor = processor;
        } else {
            this.processor = this.processor + " " + processor;
        }

    }

    /**
     * @return the biosVersion
     */
    public String getBiosVersion() {
        return biosVersion;
    }

    /**
     * @param biosVersion the biosVersion to set
     */
    public void setBiosVersion(String biosVersion) {
        this.biosVersion = biosVersion;
    }

    /**
     * @return the windowsDir
     */
    public String getWindowsDir() {
        return windowsDir;
    }

    /**
     * @param windowsDir the windowsDir to set
     */
    public void setWindowsDir(String windowsDir) {
        this.windowsDir = windowsDir;
    }

    /**
     * @return the systemDir
     */
    public String getSystemDir() {
        return systemDir;
    }

    /**
     * @param systemDir the systemDir to set
     */
    public void setSystemDir(String systemDir) {
        this.systemDir = systemDir;
    }

    /**
     * @return the bootDevice
     */
    public String getBootDevice() {
        return bootDevice;
    }

    /**
     * @param bootDevice the bootDevice to set
     */
    public void setBootDevice(String bootDevice) {
        this.bootDevice = bootDevice;
    }

    /**
     * @return the systemLocale
     */
    public String getSystemLocale() {
        return systemLocale;
    }

    /**
     * @param systemLocale the systemLocale to set
     */
    public void setSystemLocale(String systemLocale) {
        this.systemLocale = systemLocale;
    }

    /**
     * @return the inputLocale
     */
    public String getInputLocale() {
        return inputLocale;
    }

    /**
     * @param inputLocale the inputLocale to set
     */
    public void setInputLocale(String inputLocale) {
        this.inputLocale = inputLocale;
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone the timeZone to set
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return the totalPhysMemory
     */
    public String getTotalPhysMemory() {
        return totalPhysMemory;
    }

    /**
     * @param totalPhysMemory the totalPhysMemory to set
     */
    public void setTotalPhysMemory(String totalPhysMemory) {
        this.totalPhysMemory = totalPhysMemory;
    }

    /**
     * @return the availablePhysMemory
     */
    public String getAvailablePhysMemory() {
        return availablePhysMemory;
    }

    /**
     * @param availablePhysMemory the availablePhysMemory to set
     */
    public void setAvailablePhysMemory(String availablePhysMemory) {
        this.availablePhysMemory = availablePhysMemory;
    }

    /**
     * @return the virtualMemMaxSize
     */
    public String getVirtualMemMaxSize() {
        return virtualMemMaxSize;
    }

    /**
     * @param virtualMemMaxSize the virtualMemMaxSize to set
     */
    public void setVirtualMemMaxSize(String virtualMemMaxSize) {
        this.virtualMemMaxSize = virtualMemMaxSize;
    }

    /**
     * @return the virtualMemAvailable
     */
    public String getVirtualMemAvailable() {
        return virtualMemAvailable;
    }

    /**
     * @param virtualMemAvailable the virtualMemAvailable to set
     */
    public void setVirtualMemAvailable(String virtualMemAvailable) {
        this.virtualMemAvailable = virtualMemAvailable;
    }

    /**
     * @return the virtualMemInUse
     */
    public String getVirtualMemInUse() {
        return virtualMemInUse;
    }

    /**
     * @param virtualMemInUse the virtualMemInUse to set
     */
    public void setVirtualMemInUse(String virtualMemInUse) {
        this.virtualMemInUse = virtualMemInUse;
    }

    /**
     * @return the pageFileLocation
     */
    public String getPageFileLocation() {
        return pageFileLocation;
    }

    /**
     * @param pageFileLocation the pageFileLocation to set
     */
    public void setPageFileLocation(String pageFileLocation) {
        if (this.pageFileLocation == null) {
            this.pageFileLocation = pageFileLocation;
        } else {
            this.pageFileLocation = this.pageFileLocation + " " + pageFileLocation;
        }

    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the logonServer
     */
    public String getLogonServer() {
        return logonServer;
    }

    /**
     * @param logonServer the logonServer to set
     */
    public void setLogonServer(String logonServer) {
        this.logonServer = logonServer;
    }

    /**
     * @return the hotfix
     */
    public String getHotfix() {
        return hotfix;
    }

    /**
     * @param hotfix the hotfix to set
     */
    public void setHotfix(String hotfix) {
        if (this.hotfix == null) {
            this.hotfix = hotfix;
        } else {
            this.hotfix = this.hotfix + " " + hotfix;
        }

    }

    /**
     * @return the networkCard
     */
    public String getNetworkCard() {
        return networkCard;
    }

    /**
     * @param networkCard the networkCard to set
     */
    public void setNetworkCard(String networkCard) {
        if (this.networkCard == null) {
            this.networkCard = networkCard;
        } else {
            this.networkCard = this.networkCard + " " + networkCard;
        }

    }

    public String getSystemInfoValue(String key, String rawData) {
        // i.e: 
        // Host Name:                 ASUS-LAPTOP

        int posisiTitikDuaValid = 0;
        String dataBersih = null;

        if (rawData.contains(key)) {

            // lanjut process karena data ini ada
            // dan siap diparsing
            posisiTitikDuaValid = key.length() + 1;
            // pembuangan keynya tersisa value dengan tab spaces
            rawData = rawData.substring(posisiTitikDuaValid);
            dataBersih = rawData.trim();
        }

        return dataBersih;
    }

}
