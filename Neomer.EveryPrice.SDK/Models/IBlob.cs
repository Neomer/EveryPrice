using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IBlob : IEntity
    {

        DateTime CreationDate { get; set; }

        byte[] Data { get; set; }

    }

}
