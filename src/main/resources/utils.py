import sys
import numpy as np 
import json
from copy import deepcopy

def Str2Value(sourceString,targetType,blocksize=[512,512,16]):
    if targetType=='coordinate':
        coorNum=sourceString.count('_')+1
        sind=-1
        value=np.zeros((coorNum))
        for i in range(coorNum):
            ind = sourceString.find('_',sind+1)            
            if ind>0:
                 value[i]=int(sourceString[sind+1:ind])
                 sind=ind
            else:
                 value[i]=int(sourceString[sind+1:])
    elif targetType=='blockPath':
        value=np.zeros((3))
        sind=0
        tind=0
        for i in range(3):
            sind=sourceString.find('_',tind+1)
            tind=sourceString.find('/',tind+1)
            value[2-i]=int(sourceString[sind+1:tind])
        value[2]=value[2]*blocksize[2]+int(sourceString[tind+6:])
    else:
        print('string type error')
    return value

def Tracing(dataBlock, path, finishPt):
    # test entry
    newNode=np.zeros((1,7))
    newNode[0,1]=1 # type
    newNode[0,5]=1 # radius
    for i in range(100): 
        newNode[0,2:5]=np.array([i+1,i+1,i+1])
        path=SwcAdd(path,newNode)
    return path

def SwcAdd(path, newPt):
    newPt[0,0]=path[-1,0]+1
    newPt[0,-1]=path[-1,0]
    path=np.concatenate([path,newPt],axis=0)
    return path

def JsonSave(filename,data):
    with open(filename,'w') as json_file:
        json_file.write(json.dumps(data))

def JsonLoad(filename):
    with open(filename,'r') as json_file:
        data=json.load(json_file)
    return data

def BlockGenerate():
    return True

def BlockIDFind(blockCoordinate, direct, axis):
    nextCoordinate=deepcopy(blockCoordinate)
    nextCoordinate[axis]+=direct
    return nextCoordinate    

def CoordinateTrans(dataSet,databBock,resourceCoordinate,type,blocksize=np.array([512,512,16])):
    # this function implement the  transformtion of local and global coordinate  
    # dataSet- path of dataset 
    # dataBlock- path of block
    # resourceCoordinate- global coordinate 
    # type- transform mode: 1:global to local ;2:local to global 
    # return- target coordinate
    blockCoordinate=Str2Value(dataBlock,'blockPath')
    resourceCoordinate = np.array(Str2Value(resourceCoordinate,'coordinate'))
    #print(repr(resourceCoordinate))
    if type == 1:
        targetCoordinate=resourceCoordinate%blocksize
    elif type == 2:
        targetCoordinate=np.zeros((3))
        for i in range(3):
            targetCoordinate[i] = blockCoordinate[i] * blocksize[i]+resourceCoordinate[i]
    return targetCoordinate

def PointConnect(dataSet,dataBlock,coordinateA,coordinateB):
    # dataSet- path of dataset 
    # dataBlock- path of dataBlock
    # coordinateA- starting segments, shape 7*1, 
    # coordinateB- finishing point, shape 7*1, example [0,1,x,y,z,1,0] the tmp ids are set to 0
    # return -swcfilepath, shape 7*N, segment result
    path = np.reshape(Str2Value(coordinateA,'coordinate'),[1,7])
    finishPt = np.reshape(Str2Value(coordinateB,'coordinate'),[1,7])
    print(repr(path))
    print(repr(finishPt))
    return Tracing(dataBlock, path, finishPt)
    

       
# CoordinateTrans test 

dataSet=sys.argv[1]#"/datapath"
dataBlock=sys.argv[2]# "z_90/y_1003/x_300/block100"
xyz1=sys.argv[3]#"763_299_776"
xyz2=sys.argv[4]#"200_300_15"

resourceCoordinate=xyz1
targetCoordinate = CoordinateTrans(dataSet, dataBlock, xyz1, type= 1)
print(dataBlock+' '+xyz1+' '+xyz2)
print('type 1:'+repr(targetCoordinate))
targetCoordinate = CoordinateTrans(dataSet, dataBlock, xyz2, type= 2)
print('type 2:'+repr(targetCoordinate))



