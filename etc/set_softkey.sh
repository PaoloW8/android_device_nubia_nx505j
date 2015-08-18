#!/system/bin/sh
# Copyright (c) 2015 PaoloW8

sleep 2
softkey=`settings get secure dev_force_show_navbar`
file="/system/usr/keylayout/Generic.kl"
to_do="$(cat $file | grep "key 102   HOME" | cut -b1)"

mount -o rw,remount /system

case "$softkey" in
	"0")
		if [ "$to_do" == "#" ]
		then
			sed -i "s/#key 102   HOME/key 102   HOME/" $file
			sed -i "s/#key 139   MENU/key 139   MENU/" $file
			sed -i "s/#key 158   BACK/key 158   BACK/" $file
		fi
		;;
	"1")
		if [ "$to_do" != "#" ]
		then
			sed -i "s/key 102   HOME/#key 102   HOME/" $file
			sed -i "s/key 139   MENU/#key 139   MENU/" $file
			sed -i "s/key 158   BACK/#key 158   BACK/" $file
		fi
		;;
esac

mount -o ro,remount /system

setprop softkey.change 0

