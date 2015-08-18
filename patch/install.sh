echo " "
patchdir="$PWD"
cd ../../../..

cd frameworks/base/packages/SystemUI
echo "Applying SystemUI patch..."
git apply $patchdir/01.patch
git apply $patchdir/02.patch
git apply $patchdir/03.patch
git apply $patchdir/04.patch
git apply $patchdir/11.patch
git apply $patchdir/12.patch
git apply $patchdir/headset_2.patch
git apply $patchdir/headset_3.patch
echo " "

cd ../../../..
cd frameworks/base/core
echo "Applying Framerworks/core patch..."
git apply $patchdir/09.patch
git apply $patchdir/10.patch
git apply $patchdir/headset_1.patch
git apply $patchdir/headset_4.patch
git apply $patchdir/3fingers_2.patch
echo " "

cd ..
cd policy
echo "Applying Framerworks/policy patch..."
git apply $patchdir/3fingers_3.patch
echo " "

cd ../../..
cd packages/apps/Settings
echo "Applying Settings patch..."
git apply $patchdir/07.patch
git apply $patchdir/08.patch
git apply $patchdir/headset_5.patch
git apply $patchdir/3fingers_1.patch
echo " "

echo "Adding Files if not exist..."
cd ../../..

cd frameworks/base/policy
if [ ! -e src/com/android/internal/policy/impl/OPGesturesListener.java ]; then
	git apply $patchdir/3fingers_4.patch
	echo "OPGesturesListener.java added"
fi

cd ..
cd packages
if [ ! -d SystemUIExt ]; then
	mkdir SystemUIExt
	cd SystemUIExt
	git apply $patchdir/SystemUIExt.patch
	echo "SystemUIExt added"
	cd ..
fi

cd SystemUI
if [ ! -e res/drawable/stat_sys_headset.xml ]; then
	git apply $patchdir/headset_6.patch
	echo "stat_sys_headset.xml added"
fi

echo " "
echo "done !"
cd $patchdir

