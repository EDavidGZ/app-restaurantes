const BuildC = exports;

with (exports) {

var em$attrs = exports.em$attrs = {};

// -------- IMPORTS -------- //


// -------- DIMENSIONS -------- //


// -------- DECLARATIONS -------- //

var arch = exports.arch = null;
var bootFlash = exports.bootFlash = false;
var bootLoader = exports.bootLoader = false;
var compiler = exports.compiler = null;
var cpu = exports.cpu = null;
var jlinkDev = exports.jlinkDev = null;
var mcu = exports.mcu = null;
var optimize = exports.optimize = null;
var curPval = exports.curPval = null;

// -------- FUNCTIONS -------- //

const em$preconfigure = exports.em$preconfigure = function() {
    if (getProp("em.build.Arch")) ($$unsealed('em.lang/BuildC//arch') ? (arch = curPval) : arch);
    if (getProp("em.build.BootFlash")) ($$unsealed('em.lang/BuildC//bootFlash') ? (bootFlash = true) : bootFlash);
    if (getProp("em.lang.BootLoader")) ($$unsealed('em.lang/BuildC//bootLoader') ? (bootLoader = true) : bootLoader);
    if (getProp("em.build.Compiler")) ($$unsealed('em.lang/BuildC//compiler') ? (compiler = curPval) : compiler);
    if (getProp("em.build.Cpu")) ($$unsealed('em.lang/BuildC//cpu') ? (cpu = curPval) : cpu);
    if (getProp("em.build.JlinkDev")) ($$unsealed('em.lang/BuildC//jlinkDev') ? (jlinkDev = curPval) : jlinkDev);
    if (getProp("em.build.Mcu")) ($$unsealed('em.lang/BuildC//mcu') ? (mcu = curPval) : mcu);
    if (getProp("em.build.Optimize")) ($$unsealed('em.lang/BuildC//optimize') ? (optimize = curPval) : optimize);
}

const getProp = exports.getProp = function(pname) {
    return curPval = em$props.get(pname, null);
}

exports.em$cname = 'em_lang_BuildC';
exports.em$isModule = false;
exports.em$upath = 'em.lang/BuildC';
exports.em$val_arch = () => arch;
exports.em$val_bootFlash = () => bootFlash;
exports.em$val_bootLoader = () => bootLoader;
exports.em$val_compiler = () => compiler;
exports.em$val_cpu = () => cpu;
exports.em$val_jlinkDev = () => jlinkDev;
exports.em$val_mcu = () => mcu;
exports.em$val_optimize = () => optimize;
exports.em$val_curPval = () => curPval;

};
