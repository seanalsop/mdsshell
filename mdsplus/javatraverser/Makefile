SHELL=/bin/sh
top_srcdir=..
RM=$(top_srcdir)/devscripts/rm_if
prefix=/usr/local/mdsplus
exec_prefix=/usr/local/mdsplus
JAVADIR = $(top_srcdir)/java/classes
JAVAC = /usr/bin/javac  -classpath ../java/classes/jScope.jar:.
JAR = /usr/bin/jar

RMIC = $(JDK_ROOT)/bin/rmic
INSTALL=../conf/install-sh -c

DEVICES = TRCHSetup.java\
ADC265Setup.java\
E1463Setup.java\
VMEWavesSetup.java\
MPBDecoderSetup.java\
RFXClockSetup.java \
RFXPulseSetup.java \
RFXDClockSetup.java \
RFXGClockSetup.java \
MPBEncoderSetup.java \
RFXTimingSetup.java \
MPBRecorderSetup.java \
TRCFSetup.java \
Aurora14Setup.java \
ACQD265Setup.java \
NI6071ESetup.java\
PELLETSetup.java\
L5613Setup.java\
RFXTimesSetup.java\
RFXPoloidalSetup.java\
RFXToroidalSetup.java\
RFXPMSetupSetup.java\
RFXPCSetupSetup.java \
RFXPVSetupSetup.java \
RFXTFSetupSetup.java \
RFXABUnitsSetup.java \
RFXChopperSetupSetup.java \
RFXInverterSetupSetup.java \
RFXFeedforwardSetupSetup.java \
RFXPRConfigSetup.java \
TR10Setup.java \
K3115Setup.java \
DIO2Setup.java \
DIO2EncoderSetup.java \
WE7000Setup.java \
WE7116Setup.java \
WE7275Setup.java \
WEGroupSetup.java \
RFXControlSetup.java \
T2ControlSetup.java \
TR32Setup.java \
FR10Setup.java \
RFXMOPSetup.java \
RFXANSALDOSetup.java

SOURCES = ActionData.java\
ActionEditor.java\
ApdData.java\
ArgEditor.java\
ArrayData.java\
AtomicData.java\
AxisEditor.java\
ByteArray.java\
ByteData.java\
CallData.java\
CompoundData.java\
ConditionData.java\
ConglomData.java\
Data.java\
DataChangeEvent.java\
DataChangeListener.java\
DataEditor.java\
DataId.java\
DataListener.java\
Database.java\
DatabaseException.java\
DependencyData.java\
DeviceField.java\
DeviceChoice.java\
DeviceDispatch.java\
DeviceDispatchField.java\
DeviceComponent.java\
DeviceSetup.java\
DeviceSetupBeanInfo.java\
DeviceButtons.java\
DeviceOk.java\
DeviceReset.java\
DeviceControl.java\
DeviceApply.java\
DeviceCancel.java\
DeviceWave.java\
DimensionData.java\
DispatchData.java\
DispatchEditor.java\
DisplayData.java\
DisplayNci.java\
DoubleArray.java\
DoubleData.java\
Editor.java\
EventData.java\
ExprEditor.java\
FloatArray.java\
FloatData.java\
FrameRepository.java\
FunctionData.java\
IdentData.java\
IllegalDataException.java\
IntArray.java\
IntData.java\
LabeledExprEditor.java\
MethodData.java\
MethodEditor.java\
ModifyData.java\
NidData.java\
Node.java\
NodeBeanInfo.java\
NodeDataPropertyEditor.java\
NodeDisplayData.java\
NodeDisplayNci.java\
NodeEditor.java\
NodeId.java\
NodeInfo.java\
NodeInfoPropertyEditor.java\
NodeModifyData.java\
NodePropertyEditor.java\
OctaArray.java\
OctaData.java\
ParameterData.java\
PathData.java\
ProcedureData.java\
ProcedureEditor.java\
ProgramData.java\
ProgramEditor.java\
QuadArray.java\
QuadData.java\
RangeData.java\
RangeEditor.java\
RemoteTree.java\
RoutineData.java\
RoutineEditor.java\
ShortArray.java\
ShortData.java\
SignalData.java\
SlopeData.java\
StringArray.java\
StringData.java\
SyntaxException.java\
TaskEditor.java\
Tree.java\
TreeDialog.java\
TreeNode.java\
TreeServer.java\
UnsupportedDataException.java\
WindowData.java\
WindowEditor.java\
WithErrorData.java\
WithUnitsData.java\
jTraverser.java\
DecompileTree.java\
CompileTree.java

GIFS = DeviceApply.gif \
       DeviceChoice.gif \
       DeviceReset.gif \
       compound.gif \
       signal.gif \
       text.gif \
       DeviceButtons.gif \
       DeviceDispatch.gif \
       DeviceSetup.gif \
       device.gif \
       structure.gif \
       window.gif \
       DeviceCancel.gif \
       DeviceField.gif \
       action.gif \
       dispatch.gif \
       subtree.gif \
       DeviceChannel.gif \
       DeviceOk.gif \
       axis.gif \
       numeric.gif \
       task.gif

all: $(top_srcdir)/bin/ $(top_srcdir)/bin/jTraverser $(top_srcdir)/java/ $(top_srcdir)/java/classes \
     $(top_srcdir)/java/classes/jTraverser.jar

clean:
	@ $(RM) *.class
	@ $(RM) $(top_srcdir)/bin/jTraverser
	@ $(RM) $(top_srcdir)/java/classes/jTraverser.jar

depend: ; @ :

install:
	if [ ! -d /usr/local/mdsplus/java/classes ] ; then \
                mkdirhier /usr/local/mdsplus/java/classes; \
                chmod -R 755 /usr/local/mdsplus/java; \
        fi;
	$(INSTALL) -m 644  $(top_srcdir)/java/classes/jTraverser.jar /usr/local/mdsplus/java/classes
	$(INSTALL) -m 755  $(top_srcdir)/bin/jTraverser /usr/local/mdsplus/bin

$(top_srcdir)/java/ : ; mkdir $(top_srcdir)/java/

$(top_srcdir)/java/classes : ; mkdir $(top_srcdir)/java/classes

$(top_srcdir)/java/classes/jTraverser.jar: jTraverser.class
	$(JAR) c0f $@ *.class $(GIFS)

jTraverser.class: 	$(SOURCES) $(DEVICES)
	$(JAVAC) -g $(SOURCES)
	$(JAVAC) -g $(DEVICES)
	$(RMIC) TreeServer 

$(top_srcdir)/bin/ : ; mkdir $(top_srcdir)/bin/

$(top_srcdir)/bin/jTraverser : jTraverser.template
	cp jTraverser.template $@
	chmod a+x $@
