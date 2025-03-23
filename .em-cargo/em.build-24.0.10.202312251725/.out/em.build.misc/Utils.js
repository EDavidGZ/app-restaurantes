const Utils = exports;

with (exports) {

var em$attrs = exports.em$attrs = {};

// -------- IMPORTS -------- //

const BuildC = exports.BuildC = require('c:/Users/jgar0/Documents/code/uveg_code/restaurantes/.em-cargo/em.build-24.0.10.202312251725/.out/em.lang/BuildC.js');

// -------- DIMENSIONS -------- //


// -------- DECLARATIONS -------- //

var em$exclude = exports.em$exclude = false;
var em$export = exports.em$export = false;
var em$traceGrp = exports.em$traceGrp = null;
var em$used = exports.em$used = false;
var em$tracePri = exports.em$tracePri = 0;
var em$fail = exports.em$fail = '&em_build_misc_Utils::em$fail';
var em$halt = exports.em$halt = '&em_build_misc_Utils::em$halt';
var em$reset = exports.em$reset = '&em_build_misc_Utils::em$reset';
var em$run = exports.em$run = '&em_build_misc_Utils::em$run';
var em$shutdown = exports.em$shutdown = '&em_build_misc_Utils::em$shutdown';
var em$startup = exports.em$startup = '&em_build_misc_Utils::em$startup';
var em$startupDone = exports.em$startupDone = '&em_build_misc_Utils::em$startupDone';
exports.SectDesc = class SectDesc extends $$Agg { constructor(obj, cn) { super(cn); 
    this.addr = 0;
    this.size = 0;
    this.memory = null;
    this.sectid = null;
    if (obj) this.$$asn(obj);
}};
var sectTab = exports.sectTab = new $$Arr([], undefined, (cn) => null);

// -------- FUNCTIONS -------- //

const addInclude = exports.addInclude = function(path) {
    let cincs = em$props.get('em.build.CompileIncludes');
    cincs += em$find(path) + ';';
    em$props.set('em.build.CompileIncludes', cincs);
}

const addObjectFile = exports.addObjectFile = function(path) {
    let lobjs = em$props.get('em.build.LinkerObjects');
    lobjs += em$find(path) + ';';
    em$props.set('em.build.LinkerObjects', lobjs);
}

const addOption = exports.addOption = function(opt) {
    let copts = em$props.get('em.build.CompileOptions');
    copts += " " + opt;
    em$props.set('em.build.CompileOptions', copts);
}

const addSection = exports.addSection = function(addr, size, memory, sectid) {
    let sect = new SectDesc;
    sect.addr = addr;
    sect.size = size;
    sect.memory = memory;
    sect.sectid = sectid;
    sectTab.$$set(sectTab.length++, sect);
}

const copy = exports.copy = function(dstdir, srcfile, srcpkg) {
    let srcpath = em$find(`${srcpkg}/${srcfile}`);
    if (!srcpath) return;
    let dstpath = `${dstdir}/${srcfile}`;
    $Fs.writeFileSync(dstpath, $Fs.readFileSync(srcpath));
}

const findCanonical = exports.findCanonical = function(path) {
    return em$find(path).replace(/\\/g, '/');
}

const genDefines = exports.genDefines = function() {
    let em$l = '';
    let isWarmSrc = "<" + em$props.get('em.lang.Builder').split('/')[0] + "/is_warm.c>";
    em$l += `    -D__EM_ARCH_${BuildC.arch}__ \\\n`;
    em$l += `    -D__EM_BOOT__=${BuildC.bootLoader ? 1 : 0} \\\n`;
    em$l += `    -D__EM_BOOT_FLASH__=${BuildC.bootFlash ? 1 : 0} \\\n`;
    em$l += `    -D__EM_COMPILER_${BuildC.compiler}__ \\\n`;
    em$l += `    -D__EM_CPU_${mkId(BuildC.cpu)}__ \\\n`;
    em$l += `    -D__EM_MCU_${BuildC.mcu}__ \\\n`;
    em$l += `    -D__EM_IS_WARM_SRC=${isWarmSrc} \\\n`;
    em$l += `    -D__EM_LANG__=1 \\\n`;
    return em$l;
}

const getSections = exports.getSections = function() {
    return sectTab;
}

const mkId = exports.mkId = function(pval) {
    if (!pval) return pval;
    return pval.replace(/-/g, '_').replace(/\+/g, 'plus');
}

const setOptimize = exports.setOptimize = function(level) {
    let copts = em$props.get('em.build.CompileOptions');
    if (copts.match(/ -O/) || !level) return;
    copts += " -O" + level;
    em$props.set('em.build.CompileOptions', copts);
}

exports.em$cname = 'em_build_misc_Utils';
exports.em$isModule = true;
exports.em$upath = 'em.build.misc/Utils';
exports.em$val_em$exclude = () => em$exclude;
exports.em$val_em$export = () => em$export;
exports.em$val_em$traceGrp = () => em$traceGrp;
exports.em$val_em$used = () => em$used;
exports.em$val_em$tracePri = () => em$tracePri;
exports.em$val_sectTab = () => sectTab;

};
