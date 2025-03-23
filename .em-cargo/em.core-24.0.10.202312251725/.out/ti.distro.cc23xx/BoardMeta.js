const BoardMeta = exports;

with (exports) {

var em$attrs = exports.em$attrs = {};

// -------- IMPORTS -------- //

const BaseMeta = exports.BaseMeta = require('c:/Users/jgar0/Documents/code/uveg_code/restaurantes/.em-cargo/em.core-24.0.10.202312251725/.out/em.utils/BoardMeta.js');

// -------- DIMENSIONS -------- //


// -------- DECLARATIONS -------- //

var em$exclude = exports.em$exclude = false;
var em$export = exports.em$export = false;
var em$traceGrp = exports.em$traceGrp = null;
var em$used = exports.em$used = false;
var em$tracePri = exports.em$tracePri = 0;
var em$fail = exports.em$fail = '&ti_distro_cc23xx_BoardMeta::em$fail';
var em$halt = exports.em$halt = '&ti_distro_cc23xx_BoardMeta::em$halt';
var em$reset = exports.em$reset = '&ti_distro_cc23xx_BoardMeta::em$reset';
var em$run = exports.em$run = '&ti_distro_cc23xx_BoardMeta::em$run';
var em$shutdown = exports.em$shutdown = '&ti_distro_cc23xx_BoardMeta::em$shutdown';
var em$startup = exports.em$startup = '&ti_distro_cc23xx_BoardMeta::em$startup';
var em$startupDone = exports.em$startupDone = '&ti_distro_cc23xx_BoardMeta::em$startupDone';
var DrvDesc = exports.DrvDesc = BaseMeta.DrvDesc;
exports.PinMap = class PinMap extends $$Agg { constructor(obj, cn) { super(cn); 
    this.appBut = 0;
    this.appLed = 0;
    this.appOut = 0;
    this.extFlashCS = 0;
    this.extFlashCLK = 0;
    this.extFlashPICO = 0;
    this.extFlashPOCI = 0;
    this.sysDbgA = 0;
    this.sysDbgB = 0;
    this.sysDbgC = 0;
    this.sysDbgD = 0;
    this.sysLed = 0;
    if (obj) this.$$asn(obj);
}};
exports.Record = class Record extends $$Agg { constructor(obj, cn) { super(cn); 
    this.activeLowLeds = false;
    this.baudRate = 0;
    this.clockFreq = 0;
    this.extFlashDisable = false;
    this.lfXtalEnable = false;
    this.pinMap = null;
    this.drvDescs = new $$Arr([], undefined, (cn) => null);
    if (obj) this.$$asn(obj);
}};
var baseFileLoc = exports.baseFileLoc = "ti.distro.cc23xx/em-boards";
var attrNames = exports.attrNames = new $$Arr(["$inherits", "$overrides", "activeLowLeds", "baudRate", "clockFreq", "extFlashDisable", "lfXtalEnable"], undefined, (cn) => null);
var pinNames = exports.pinNames = new $$Arr(["appBut", "appLed", "appOut", "extFlashCS", "extFlashCLK", "extFlashPICO", "extFlashPOCI", "sysDbgA", "sysDbgB", "sysDbgC", "sysDbgD", "sysLed"], undefined, (cn) => null);

// -------- FUNCTIONS -------- //

exports.em$cname = 'ti_distro_cc23xx_BoardMeta';
exports.em$isModule = true;
exports.em$upath = 'ti.distro.cc23xx/BoardMeta';
exports.em$val_em$exclude = () => em$exclude;
exports.em$val_em$export = () => em$export;
exports.em$val_em$traceGrp = () => em$traceGrp;
exports.em$val_em$used = () => em$used;
exports.em$val_em$tracePri = () => em$tracePri;
exports.em$val_baseFileLoc = () => baseFileLoc;
exports.em$val_attrNames = () => attrNames;
exports.em$val_pinNames = () => pinNames;

};
