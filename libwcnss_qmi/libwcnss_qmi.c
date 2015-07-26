/*
 * Copyright (C) 2014, The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_NDEBUG 0

#define LOG_TAG "WCNSS_NX505J"

#include <cutils/log.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define WCNSS_INI "/persist/WCNSS_qcom_cfg.ini"

int wcnss_init_qmi(void)
{
  /* empty */
  return 0;
}

int wcnss_qmi_get_wlan_address(unsigned char *pBdAddr)
{
  FILE *fp;
  int i;
  
  ALOGE("wcnss_qmi_get_wlan_address");
  
  fp = fopen(WCNSS_INI,"r");
  if(fp == NULL){
    return -1;
  }
  
  char buf[256];
  while (fgets (buf, sizeof(buf), fp)) {
    if((strncmp("Intf0MacAddress=",buf,16) == 0) && strlen(buf) == 29){
      char* pos = &buf[16];
      for(i = 0; i < 6; i++) {
	sscanf(pos, "%2hhx", &pBdAddr[i]);
	pos += 2 * sizeof(char);
      }
      break;
    }
  }
  
  ALOGE("Found MAC address: %02hhx:%02hhx:%02hhx:%02hhx:%02hhx:%02hhx\n",
	pBdAddr[0],
	pBdAddr[1],
	pBdAddr[2],
	pBdAddr[3],
	pBdAddr[4],
	pBdAddr[5]);
  
  fclose(fp);
  return 0;
}

void wcnss_qmi_deinit(void)
{
  /* empty */
}
