const BuilderBaseI = exports;

with (exports) {

var em$attrs = exports.em$attrs = {};

// -------- IMPORTS -------- //

const BuilderI = exports.BuilderI = require('c:/Users/jgar0/Documents/code/uveg_code/restaurantes/.em-cargo/em.build-24.0.10.202312251725/.out/em.lang/BuilderI.js');

// -------- DIMENSIONS -------- //


// -------- DECLARATIONS -------- //

exports.CompileInfo = class CompileInfo extends $$Agg { constructor(obj, cn) { super(cn); 
    this.errMsgs = new $$Arr([], undefined, (cn) => null);
    this.imageSizes = null;
    this.procStat = 0;
    if (obj) this.$$asn(obj);
}};
exports.TypeInfo = class TypeInfo extends $$Agg { constructor(obj, cn) { super(cn); 
    this.ARG = new $$Arr([], this.$$cn+'.ARG', (cn) => 0, 2);
    this.CHAR = new $$Arr([], this.$$cn+'.CHAR', (cn) => 0, 2);
    this.INT = new $$Arr([], this.$$cn+'.INT', (cn) => 0, 2);
    this.INT8 = new $$Arr([], this.$$cn+'.INT8', (cn) => 0, 2);
    this.INT16 = new $$Arr([], this.$$cn+'.INT16', (cn) => 0, 2);
    this.INT32 = new $$Arr([], this.$$cn+'.INT32', (cn) => 0, 2);
    this.LONG = new $$Arr([], this.$$cn+'.LONG', (cn) => 0, 2);
    this.PTR = new $$Arr([], this.$$cn+'.PTR', (cn) => 0, 2);
    this.SHORT = new $$Arr([], this.$$cn+'.SHORT', (cn) => 0, 2);
    this.SIZE = new $$Arr([], this.$$cn+'.SIZE', (cn) => 0, 2);
    if (obj) this.$$asn(obj);
}};

exports.em$cname = 'em_build_misc_BuilderBaseI';
exports.em$isModule = false;
exports.em$upath = 'em.build.misc/BuilderBaseI';
exports.em$val_em$exclude = () => em$exclude;
exports.em$val_em$export = () => em$export;
exports.em$val_em$traceGrp = () => em$traceGrp;
exports.em$val_em$used = () => em$used;
exports.em$val_em$tracePri = () => em$tracePri;
exports.em$val_arch = () => arch;
exports.em$val_cpu = () => cpu;
exports.em$val_mcu = () => mcu;

};
