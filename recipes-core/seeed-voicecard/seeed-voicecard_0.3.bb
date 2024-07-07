SUMMARY = "Seeed Voicecard driver and configuration"
DESCRIPTION = "Seeed Voicecard driver and configuration for Raspberry Pi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://seeed-voicecard \
           file://seeed-voicecard.service \
           file://configs/ \
"

KERNEL_MODULE_AUTOLOAD += "snd-soc-seeed-voicecard snd-soc-wm8960"

inherit systemd allarch deploy
DEPENDS = "systemd"

S = "${WORKDIR}"

do_install() {
    # Install systemd script
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/seeed-voicecard.service ${D}${systemd_unitdir}/system
    install -d ${D}${bindir}
    install -m 0755 ${S}/seeed-voicecard ${D}${bindir}

    # Install ALSA configs
    install -d ${D}${sysconfdir}/voicecard
    cp ${S}/configs/*.conf ${D}${sysconfdir}/voicecard/
    cp ${S}/configs/*.state ${D}${sysconfdir}/voicecard/
}

FILES:${PN} += " \
    ${systemd_unitdir}/system/ \
    ${bindir}/seeed-voicecard \
"

PACKAGE_ARCH = "all"
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "seeed-voicecard.service"
SYSTEMD_PACKAGES = "${PN}"
RDEPENDS:${PN} += "alsa-utils bash"
